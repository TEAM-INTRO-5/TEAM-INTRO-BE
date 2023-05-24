package com.fastcampus05.zillinks.core.auth.token.service;

import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
import com.fastcampus05.zillinks.core.auth.token.dto.TokenResponse;
import com.fastcampus05.zillinks.core.auth.token.model.RefreshToken;
import com.fastcampus05.zillinks.core.auth.token.model.RefreshTokenRepository;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse generateAccessToken(String refreshToken, Long userId, List<String> validList, Boolean isWithInWeek) {
        RefreshToken rtkPS = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));
        if (!rtkPS.getValidList().get(0).equals(validList.get(0)) ||
                !rtkPS.getValidList().get(1).equals(validList.get(1)) ||
                !rtkPS.getUserId().equals(userId)) {
            refreshTokenRepository.delete(rtkPS);
            throw new Exception401("인증되지 않았습니다.");
        }
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new Exception401("인증되지 않았습니다.")
        );

        // refreshToken의 만료 기간이 7일 이내일 경우 새롭게 갱신하여 보내준다.
        String jwt = null;
        // 추가할 부분
        log.info("isWithInWeek={}", isWithInWeek);
        if (isWithInWeek) {
            refreshTokenRepository.delete(rtkPS);
            RefreshToken rtk = new RefreshToken(UUID.randomUUID().toString(), userPS.getId(), validList);
            try {
                refreshTokenRepository.save(rtk);
            } catch (Exception e) {
                throw new Exception500("Token 생성에 실패하였습니다.");
            }
            jwt = MyJwtProvider.createRefreshToken(rtk);
        }
        String accessToken = MyJwtProvider.createAccessToken(userPS);
        return new TokenResponse(jwt, accessToken);
    }
}
