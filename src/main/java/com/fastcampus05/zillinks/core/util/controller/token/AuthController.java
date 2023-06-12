package com.fastcampus05.zillinks.core.util.controller.token;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception401;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.token.TokenResponse;
import com.fastcampus05.zillinks.core.util.service.token.RefreshTokenService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
    })
    @Parameters({
            @Parameter(name = "rememberMe", description = "자동 로그인 체크 유무", example = "true"),
    })
    @PostMapping("/accessToken")
    public ResponseEntity<?> generateAccessToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String prefixJwt = request.getHeader(MyJwtProvider.HEADER);

        if (prefixJwt.isEmpty() && prefixJwt.isBlank()) {
            throw new Exception400("refreshToken", "refreshToken이 존재하지 않습니다.");
        }

        String refreshJwt = prefixJwt.replace(MyJwtProvider.TOKEN_PREFIX, "");
        try {
            // remember_me가 있는지 확인
            String value = "";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember_me")) {
                    value = cookie.getValue();
                    log.info("value={}", value);
                    break;
                }
            }

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
            log.info("exp={}", exp);
            Boolean isWithInWeek = timeLeft.compareTo(Duration.ofDays(7)) < 0;

            TokenResponse tokenResponse = refreshTokenService.generateAccessToken(refreshToken, validList, isWithInWeek);
            if (!StringUtils.hasText(tokenResponse.getRefreshToken())) {
                tokenResponse.setRefreshToken(MyJwtProvider.TOKEN_PREFIX + refreshJwt);
            } else {
                // check-point
                // remember_me가 true일 경우 refresh-token을 설정한 뒤 넘겨준다.
                if (!value.isEmpty()) {
                    try {
                        String rtk = URLEncoder.encode(tokenResponse.getRefreshToken(), "utf-8");
                        Cookie cookie = new Cookie("remember_me", rtk);
                        cookie.setHttpOnly(true);
                        cookie.setPath("/api"); // accessToken 재발급시에만 사용가능하도록 설정
                        cookie.setMaxAge(60 * 60 * 24 * 30);
                        // HTTPS를 사용할 경우 true로 설정
                        cookie.setSecure(false);
                        response.addCookie(cookie);
                    } catch (UnsupportedEncodingException e) {
                        throw new Exception500(e.getMessage());
                    }
                }
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
