package com.fastcampus05.zillinks.domain.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/s/authorization-test")
    public String test() {
        return "ok";
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
