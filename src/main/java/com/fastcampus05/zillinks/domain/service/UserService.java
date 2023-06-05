package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.annotation.MyLog;
import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.model.token.RefreshToken;
import com.fastcampus05.zillinks.core.util.model.token.RefreshTokenRepository;
import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
import com.fastcampus05.zillinks.domain.dto.user.UserResponse;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

//    @MyLog
//    @Transactional
//    public UserResponse.JoinOutDTO 회원가입(UserRequest.JoinInDTO joinInDTO){
//        Optional<User> userOP =userRepository.findByUsername(joinInDTO.getUsername());
//        if(userOP.isPresent()){
//            // 이 부분이 try catch 안에 있으면 Exception500에게 제어권을 뺏긴다.
//            throw new Exception400("username", "유저네임이 존재합니다");
//        }
//        String encPassword = passwordEncoder.encode(joinInDTO.getPassword()); // 60Byte
//        joinInDTO.setPassword(encPassword);
//        System.out.println("encPassword : "+encPassword);
//
//        // 디비 save 되는 쪽만 try catch로 처리하자.
//        try {
//            User userPS = userRepository.save(joinInDTO.toEntity());
//            return new UserResponse.JoinOutDTO(userPS);
//        }catch (Exception e){
//            throw new Exception500("회원가입 실패 : "+e.getMessage());
//        }
//    }

    @MyLog
    @Transactional
    public UserResponse.LoginOutDTO login(UserRequest.LoginInDTO loginInDTO, List<String> validList) {
        MyUserDetails myUserDetails;
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginInDTO.getEmail(), loginInDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new Exception401("인증되지 않았습니다");
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
        return new UserResponse.LoginOutDTO(rtk, atk);
    }

//    @MyLog
//    public UserResponse.DetailOutDTO 회원상세보기(Long id) {
//        User userPS = userRepository.findById(id).orElseThrow(
//                ()-> new Exception404("해당 유저를 찾을 수 없습니다")
//
//        );
//        return new UserResponse.DetailOutDTO(userPS);
//    }
}
