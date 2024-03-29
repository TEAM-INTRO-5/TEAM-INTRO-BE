package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
import com.fastcampus05.zillinks.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus05.zillinks.domain.dto.user.UserResponse.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "일반 회원가입", description = "일반 아이디로 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "joinInDTO")
    })
    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody @Valid UserRequest.JoinInDTO joinInDTO,
            Errors errors
    ) {
        userService.join(joinInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "소셜 회원가입", description = "구글 아이디로 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "oauthJoinInDTO")
    })
    @PostMapping("/oauthJoin")
    public ResponseEntity<?> oauthJoin(
            @RequestBody @Valid UserRequest.OauthJoinInDTO oauthJoinInDTO,
            Errors errors
    ) {
        userService.oauthJoin(oauthJoinInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "login", description = "유저의 로그인과 함께 accessToken과 refreshToken을 반환해준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "loginInDTO")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserRequest.LoginInDTO loginInDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        List<String> validList = new ArrayList<>();
        validList.add(request.getRemoteAddr());
        validList.add(request.getHeader("user-agent"));

        LoginOutDTO loginOutDTO = userService.login(loginInDTO, validList);
        ResponseDTO responseBody = new ResponseDTO<>(loginOutDTO);

        // check-point
        // remember_me가 true일 경우 refresh-token을 설정한 뒤 넘겨준다.
        if (loginInDTO.getRememberMe()) {
            try {
                String rtk = URLEncoder.encode(loginOutDTO.getRefreshToken(), "utf-8");
                Cookie cookie = new Cookie("remember_me", rtk);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // accessToken 재발급시에만 사용가능하도록 설정
                cookie.setMaxAge(60 * 60 * 24 * 30);
                // HTTPS를 사용할 경우 true로 설정
                cookie.setSecure(false);
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                throw new Exception500(e.getMessage());
            }
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "logout", description = "remember_me 쿠키를 지워준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @PostMapping("/s/user/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String value = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember_me")) {
                    value = cookie.getValue();
                    break;
                }
            }
            if (!value.isEmpty()) {
                userService.logout(value);
                try {
                    Cookie cookie = new Cookie("remember_me", URLEncoder.encode("", "utf-8"));
                    cookie.setHttpOnly(true);
                    cookie.setPath("/"); // accessToken 재발급시에만 사용가능하도록 설정
                    cookie.setMaxAge(0); // 쿠키 만료 시점을 설정
                    // HTTPS를 사용할 경우 true로 설정
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "google_login", description = "구글 로그인으로 유저의 로그인과 함께 accessToken과 refreshToken을 반환해준다. 만일 연동되어 있는 계정이 없는 경우 토큰은 반환되지 않는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OAuthLoginOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "code"),
    })
    @GetMapping("/callback")
    public ResponseEntity<?> callback(String code, HttpServletRequest request) {
        List<String> validList = new ArrayList<>();
        validList.add(request.getRemoteAddr());
        validList.add(request.getHeader("user-agent"));
        // 1. code 값 존재 유무 확인
        if (code == null || code.isEmpty())
            throw new Exception400("code", "code값이 존재하지 않습니다.");

        OAuthLoginOutDTO oAuthLoginOutDTO = userService.oauthLogin(code, validList);
        ResponseDTO responseBody = new ResponseDTO<>(oAuthLoginOutDTO);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "사업자등록번호 인증 요청", description = "사업자등록번호를 입력하면 질링스 api에 요청을 해서 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증되었습니다.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "숫자 10자리로 입력해 주세요.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "등록되지 않은 번호입니다.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "bizNum", description = "사업자등록번호", example = "2258701327", required = true)
    })
    @PostMapping("/validBizNum")
    public ResponseEntity<?> validBizNum(@RequestBody @Valid UserRequest.BizNumInDTO bizNum, Errors errors) {
        String result = userService.validBizNum(bizNum.getBizNum());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", result);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "아이디 중복 확인", description = "DB 내에 이미 존재하는 ID인지 체크하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "checkLoginIdInDTO"),
    })
    @PostMapping("/checkLoginId")
    public ResponseEntity<?> checkLoginId(
            @RequestBody @Valid UserRequest.CheckLoginIdInDTO checkLoginIdInDTO,
            Errors errors
    ) {
        userService.checkLoginId(checkLoginIdInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "이메일을 통한 아이디 찾기", description = "이메일을 통해 DB내에 매핑되어 있는 ID를 탐색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FindIdByEmailOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "findIdByEmailInDTO"),
    })
    @PostMapping("/findIdByEmail")
    public ResponseEntity<?> findIdByEmail(
            @RequestBody @Valid UserRequest.FindIdByEmailInDTO findIdByEmailInDTO,
            Errors errors
    ) {
        FindIdByEmailOutDTO findIdByEmailOutDTO = userService.findIdByEmail(findIdByEmailInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(findIdByEmailOutDTO);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "사업자등록번호를 통한 아이디 찾기", description = "사업자등록번호를 통해 DB내에 매핑되어 있는 ID를 탐색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FindIdByBizNumOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "findIdByBizNumInDTO"),
    })
    @PostMapping("/findIdByBizNum")
    public ResponseEntity<?> findIdByBizNum(
            @RequestBody @Valid UserRequest.FindIdByBizNumInDTO findIdByBizNumInDTO,
            Errors errors
    ) {
        FindIdByBizNumOutDTO findIdByBizNumOutDTO = userService.findIdByBizNum(findIdByBizNumInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(findIdByBizNumOutDTO);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "비밀번호 찾기", description = "잃어버린 비밀번호를 찾기 위한 과정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "findPasswordInDTO"),
    })
    @PutMapping("/password")
    public ResponseEntity<ResponseDTO> findPassword(
            @RequestBody @Valid UserRequest.FindPasswordInDTO findPasswordInDTO,
            Errors errors
    ) {
        userService.findPassword(findPasswordInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "비밀번호 재설정", description = "비밀번호 변경을 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updatePasswordInDTO"),
            @Parameter(name = "myUserDetails", hidden = true),
    })
    @PutMapping("/s/user/password")
    public ResponseEntity<ResponseDTO> updatePassword(
            @RequestBody @Valid UserRequest.UpdatePasswordInDTO updatePasswordInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        userService.updatePassword(updatePasswordInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "유저 기본 정보 조회", description = "유저 기본 정보 조회 - 마이페이지")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserInfoOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user")
    public ResponseEntity<ResponseDTO> userInfo(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        UserInfoOutDTO userInfoOutDTO = userService.userInfo(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", userInfoOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "유저 기본 정보 수정", description = "유저 기본 정보 수정 - 마이페이지")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "userInfoUpdateInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/s/user")
    public ResponseEntity<ResponseDTO> updateUserInfo(
            @RequestBody @Valid UserRequest.UserInfoUpdateInDTO userInfoUpdateInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        userService.updateUserInfo(userInfoUpdateInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }
    
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 - 마이페이지")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/s/user")
    public ResponseEntity<ResponseDTO> deleteUser(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        userService.deleteUser(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }
}
