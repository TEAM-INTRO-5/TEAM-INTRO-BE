package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
import com.fastcampus05.zillinks.domain.dto.user.UserResponse;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "login", description = "유저의 로그인과 함께 accessToken과 refreshToken을 반환해준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponse.LoginOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "loginInDTO", description = "로그인 정보",
                    example="{" +
                            "   \"email\":\"taeheoki@naver.com\"," +
                            "   \"password\":\"1234\"" +
                            "}"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginInDTO loginInDTO, HttpServletRequest request) {
        List<String> validList = new ArrayList<>();
        validList.add(request.getRemoteAddr());
        validList.add(request.getHeader("user-agent"));

        UserResponse.LoginOutDTO loginOutDTO = userService.login(loginInDTO, validList);
        ResponseDTO responseBody = new ResponseDTO<>(loginOutDTO);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "google_login", description = "구글 로그인으로 유저의 로그인과 함께 accessToken과 refreshToken을 반환해준다. 만일 연동되어 있는 계정이 없는 경우 토큰은 반환되지 않는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponse.OAuthLoginOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "loginInDTO", description = "로그인 정보",
                    example="{" +
                            "   \"email\":\"taeheoki@naver.com\"," +
                            "   \"password\":\"1234\"" +
                            "}"),
    })
    @GetMapping("/callback")
    public ResponseEntity<?> callback(String code, HttpServletRequest request) {
        List<String> validList = new ArrayList<>();
        validList.add(request.getRemoteAddr());
        validList.add(request.getHeader("user-agent"));
        log.info("test");
        // 1. code 값 존재 유무 확인
        if (code == null || code.isEmpty())
            throw new Exception400("code", "code값이 존재하지 않습니다.");

        UserResponse.OAuthLoginOutDTO oAuthLoginOutDTO = userService.oauthLogin(code, validList);
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

//    @MyErrorLog
//    @MyLog
//    @PostMapping("/api/joinUser")
//    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinInDTO joinInDTO, Errors errors) {
////        UserResponse.JoinOutDTO joinOutDTO = userService.회원가입(joinInDTO);
////        ResponseDTO<?> responseDTO = new ResponseDTO<>(joinOutDTO);
//        return ResponseEntity.ok(responseDTO);
//    }
//    @PostMapping("/api/user/saveItem")
//    @PostMapping("/api/user/items")
//    @PostMapping("/api/user/items/{itemId}")

//    @GetMapping("/blog/api/user/...")
//    @GetMapping("/{domain}/{인증}/{권한}/...")
//    @Ge
//    public ResponseEntity<?> detail(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
//        if(id.longValue() != myUserDetails.getUser().getId()){
//            throw new Exception403("권한이 없습니다");
//        }
//        UserResponse.DetailOutDTO detailOutDTO = userService.회원상세보기(id);
//        // System.out.println(new ObjectMapper().writeValueAsString(detailOutDTO));
//        ResponseDTO<?> responseDTO = new ResponseDTO<>(detailOutDTO);
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    @GetMapping("/s/error-test")
//    public ResponseEntity<?> error() {
//        throw new Exception500("Sentry io error test");
//    }
}
