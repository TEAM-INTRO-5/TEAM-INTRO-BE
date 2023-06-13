package com.fastcampus05.zillinks.domain.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fastcampus05.zillinks.core.annotation.MyLog;
import com.fastcampus05.zillinks.core.auth.oauth.Fetch;
import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.RandomStringGenerator;
import com.fastcampus05.zillinks.core.util.dto.oauth.GoogleToken;
import com.fastcampus05.zillinks.core.util.dto.oauth.OAuthProfile;
import com.fastcampus05.zillinks.core.util.model.token.RefreshToken;
import com.fastcampus05.zillinks.core.util.model.token.RefreshTokenRepository;
import com.fastcampus05.zillinks.core.util.service.mail.MailService;
import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
import com.fastcampus05.zillinks.domain.dto.user.UserResponse.OAuthLoginOutDTO.GoogleProfile;
import com.fastcampus05.zillinks.domain.model.user.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fastcampus05.zillinks.domain.dto.user.UserResponse.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    @Value("${google-oauth.client-id}")
    private String clientId;

    @Value("${google-oauth.client-secret}")
    private String clientSecret;

    @Value("${google-oauth.redirect-uri}")
    private String redirectUri;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final GoogleAccountRepository googleAccountRepository;


    @MyLog
    @Transactional
    public void join(UserRequest.JoinInDTO joinInDTO) {

        User user = User.builder()
                .loginId(joinInDTO.getLoginId())
                .email(joinInDTO.getEmail())
                .password(passwordEncoder.encode(joinInDTO.getPassword()))
                .bizNum(joinInDTO.getBizNum())
                .role("USER")
                .marketing(Marketing.builder()
                        .marketingEmail(false)
                        .build())
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception500("회원가입 실패 : " + e.getMessage());
        }
    }

    @MyLog
    @Transactional
    public void oauthJoin(UserRequest.OauthJoinInDTO oauthJoinInDTO) {

        User user = User.builder()
                .loginId(oauthJoinInDTO.getOAuthId())
                .email(oauthJoinInDTO.getOAuthEmail())
                .password(passwordEncoder.encode(RandomStringGenerator.generateRandomString()))
                .bizNum(oauthJoinInDTO.getBizNum())
                .role("USER")
                .marketing(Marketing.builder()
                        .marketingEmail(false)
                        .build())
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception500("유저저장 실패 : " + e.getMessage());
        }

        GoogleAccount googleAccount = GoogleAccount.builder()
                .user(user)
                .oAuthId(oauthJoinInDTO.getOAuthId())
                .oAuthEmail(oauthJoinInDTO.getOAuthEmail())
                .name(oauthJoinInDTO.getName())
                .loginedAt(null)
                .build();

        try {
            googleAccountRepository.save(googleAccount);
        } catch (Exception e) {
            throw new Exception500("회원가입 실패 : " + e.getMessage());
        }
    }

    @MyLog
    @Transactional
    public LoginOutDTO login(UserRequest.LoginInDTO loginInDTO, List<String> validList) {
        MyUserDetails myUserDetails;
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginInDTO.getLoginId(), loginInDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new Exception401("인증되지 않았습니다.");
        }
        User user = myUserDetails.getUser();
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user.getId(), validList);
        try {
            refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            throw new Exception500("Token 생성에 실패하였습니다.");
        }
        String rtk = MyJwtProvider.createRefreshToken(refreshToken);
        String atk = MyJwtProvider.createAccessToken(user);
        return new LoginOutDTO(rtk, atk);
    }

    @Transactional
    public void logout(String value) {
        DecodedJWT decodedJWT = MyJwtProvider.verify(value.replace("Bearer+", ""));
        String token = decodedJWT.getClaim("refreshToken").asString();
        RefreshToken rtk = refreshTokenRepository.findById(token)
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));
        refreshTokenRepository.delete(rtk);
    }

    @Transactional
    public OAuthLoginOutDTO oauthLogin(String code, List<String> validList) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code); // 핵심
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        ResponseEntity<String> codeEntity = Fetch.google("https://oauth2.googleapis.com/token", HttpMethod.POST, body);

        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        GoogleToken googleToken;
        OAuthProfile oAuthProfile;
        try {
            googleToken = om.readValue(codeEntity.getBody(), GoogleToken.class);

            ResponseEntity<String> tokenEntity = Fetch.google("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + googleToken.getAccessToken(), HttpMethod.GET);
            oAuthProfile = om.readValue(tokenEntity.getBody(), OAuthProfile.class);

            String oAuthId = "google_" + oAuthProfile.getId();

            Optional<GoogleAccount> googleAccountOP  = googleAccountRepository.findByOAuthId(oAuthId);
            // google 정보가 없을 경우 회원가입 진행, 토큰발행 X
            if (googleAccountOP.isEmpty()) {
                return OAuthLoginOutDTO.builder()
                        .googleProfile(new GoogleProfile(oAuthId, oAuthProfile.getEmail(), oAuthProfile.getName()))
                        .build();
            }

            // google 정보가 존재, 로그인 진행
            GoogleAccount googleAccountPS = googleAccountOP.get();
            // check-point 로그인 로그를 남길 때 일반 로그인과 소셜 로그인을 관리하는건 어떨까?
            // 로그인 정보 저장
            googleAccountPS.updateLoginedAt(LocalDateTime.now());
            User userPS = googleAccountPS.getUser();
            RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userPS.getId(), validList);
            try {
                refreshTokenRepository.save(refreshToken);
            } catch (Exception e) {
                throw new Exception500("Token 생성에 실패하였습니다.");
            }
            String rtk = MyJwtProvider.createRefreshToken(refreshToken);
            String atk = MyJwtProvider.createAccessToken(userPS);
            return OAuthLoginOutDTO.builder()
                    .refreshToken(rtk)
                    .accessToken(atk)
                    .googleProfile(new GoogleProfile(oAuthId, oAuthProfile.getEmail(), oAuthProfile.getName()))
                    .build();
        } catch (JsonProcessingException e) {
            throw new Exception500(e.getMessage());
        }
    }
    public String validBizNum(String bizNum) {
        if (userRepository.findByBizNum(bizNum).isPresent()) {
            throw new Exception400("bizNum", "이미 존재하는 사업자등록번호입니다.");
        }

        Common.zillinksApi(bizNum);
        return "인증되었습니다.";
    }

    public void checkLoginId(UserRequest.CheckLoginIdInDTO checkLoginIdInDTO) {
        Optional<User> userOP = userRepository.findByLoginId(checkLoginIdInDTO.getLoginId());
        if (userOP.isPresent()) {
            throw new Exception400("loginId", "존재하는 ID 입니다.");
        }
    }

    public FindIdByEmailOutDTO findIdByEmail(UserRequest.FindIdByEmailInDTO findIdByEmailInDTO) {
        User userOP = userRepository.findByEmail(findIdByEmailInDTO.getEmail()).orElseThrow(
                () -> new Exception400("email", "user가 존재하지 않습니다.")
        );
        return FindIdByEmailOutDTO.builder()
                .loginId(userOP.getLoginId())
                .build();
    }

    public FindIdByBizNumOutDTO findIdByBizNum(UserRequest.FindIdByBizNumInDTO findIdByBizNumInDTO) {
        User userOP = userRepository.findByBizNum(findIdByBizNumInDTO.getBizNum()).orElseThrow(
                () -> new Exception400("email", "user가 존재하지 않습니다.")
        );
        return FindIdByBizNumOutDTO.builder()
                .loginId(userOP.getLoginId())
                .build();
    }

    @Transactional
    public void findPassword(UserRequest.FindPasswordInDTO findPasswordInDTO) {
        User userPS = userRepository.findByLoginIdAndEmail(findPasswordInDTO.getLoginId(), findPasswordInDTO.getEmail())
                .orElseThrow(() -> new Exception400("login_id_and_email", "가입된 아이디/이메일 정보가 없습니다."));
        String newPassword = mailService.updatePassword(userPS.getEmail());
        userPS.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void updatePassword(UserRequest.UpdatePasswordInDTO updatePasswordInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        if (!passwordEncoder.matches(updatePasswordInDTO.getPassword(), userPS.getPassword()))
            throw new Exception400("password", "비밀번호가 일치하지 않습니다.");
        else if (updatePasswordInDTO.getPassword().equals(updatePasswordInDTO.getNewPassword()))
            throw new Exception400("new_password", "이전 비밀번호와 같은 것으로 변경할 수 없습니다.");
        userPS.updatePassword(passwordEncoder.encode(updatePasswordInDTO.getNewPassword()));
    }
}
