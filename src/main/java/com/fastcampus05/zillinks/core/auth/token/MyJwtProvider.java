package com.fastcampus05.zillinks.core.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fastcampus05.zillinks.core.util.model.token.RefreshToken;
import com.fastcampus05.zillinks.domain.model.user.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyJwtProvider {

    private static final String SUBJECT = "zillinks project";
    private static final Long ATK_EXP = 1000 * 60 * 20L; // 20분
    private static final Long RTK_EXP = 1000 * 60 * 60 * 24 * 30L; // 30일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    private static final String SECRET = "메타코딩"; // 실제 배포시 환경변수로 관리

    public static String createAccessToken(User user) {
        String atk = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + ATK_EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withClaim("bizNum", user.getBizNum())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + atk;
    }

    public static String createRefreshToken(RefreshToken refreshToken) {
        String rtk = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + RTK_EXP))
                .withClaim("refreshToken", refreshToken.getRefreshToken())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + rtk;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }
}