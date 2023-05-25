package com.fastcampus05.zillinks.core.auth.token.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
import com.fastcampus05.zillinks.core.auth.token.dto.TokenResponse;
import com.fastcampus05.zillinks.core.auth.token.service.RefreshTokenService;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "accessToken generate", description = "유저의 로그인과 함께 refreshToken과 accessToken을 반환해준다. refreshToken의 유효기간이 7일 이내일 경우 갱신해준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "NOT FOUND"),
            @ApiResponse(responseCode = "404", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Parameters({
            @Parameter(name = "request", description = "요청 정보", example="request")
    })
    @PostMapping("/accessToken")
    public ResponseEntity<?> generateAccessToken(HttpServletRequest request, @CookieValue("refreshToken") String refreshJwt) {

        if (refreshJwt.isEmpty() && refreshJwt.isBlank()) {
            throw new Exception400("refreshToken", "refreshToken이 존재하지 않습니다.");
        }
        try {
            // refreshToken이 탈취당했는지 확인하는 자료
            DecodedJWT decodedJWT = MyJwtProvider.verify(refreshJwt);
            String refreshToken = decodedJWT.getClaim("refreshToken").asString();
            Long userId = decodedJWT.getClaim("id").asLong();
            List<String> validList = new ArrayList<>();
            validList.add(request.getRemoteAddr());
            validList.add(request.getHeader("user-agent"));

            // refreshToken의 만료까지 7일 이내인지 확인
            Instant exp = decodedJWT.getClaim("exp").asDate().toInstant();
            Instant now = Instant.now();
            Duration timeLeft = Duration.between(now, exp);
            Boolean isWithInWeek = timeLeft.compareTo(Duration.ofDays(7)) < 0;

            TokenResponse tokenResponse = refreshTokenService.generateAccessToken(refreshToken, validList, isWithInWeek);
            if (!StringUtils.hasText(tokenResponse.getRefreshToken())) {
                tokenResponse.setRefreshToken(refreshJwt);
            }
            ResponseDTO responseBody = new ResponseDTO(tokenResponse);
            return ResponseEntity.ok(responseBody);
        } catch (SignatureVerificationException sve) {
            throw new Exception401("refreshToken 검증 실패");
        } catch (TokenExpiredException tee) {
            throw new Exception401("refreshToken 만료됨");
        }
    }
}
