//package com.fastcampus05.zillinks.domain.controller;
//
//import com.fastcampus05.zillinks.core.annotation.MyErrorLog;
//import com.fastcampus05.zillinks.core.annotation.MyLog;
//import com.fastcampus05.zillinks.core.auth.jwt.MyJwtProvider;
//import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
//import com.fastcampus05.zillinks.core.exception.Exception403;
//import com.fastcampus05.zillinks.core.exception.Exception500;
//import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
//import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
//import com.fastcampus05.zillinks.domain.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RequiredArgsConstructor
//@RestController
//public class UserController {
//
//    private final UserService userService;
//
//    @MyErrorLog
//    @MyLog
//    @PostMapping("/api/joinUser")
//    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinInDTO joinInDTO, Errors errors) {
////        UserResponse.JoinOutDTO joinOutDTO = userService.회원가입(joinInDTO);
////        ResponseDTO<?> responseDTO = new ResponseDTO<>(joinOutDTO);
//        return ResponseEntity.ok(responseDTO);
//    }
////    @PostMapping("/api/user/saveItem")
////    @PostMapping("/api/user/items")
////    @PostMapping("/api/user/items/{itemId}")
//
//    @PostMapping("/api/loginUser")
//    public ResponseEntity<?> login(@RequestBody UserRequest.LoginInDTO loginInDTO){
//        String jwt = userService.로그인(loginInDTO);
//        ResponseDTO<?> responseDTO = new ResponseDTO<>();
//        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body(responseDTO);
//    }
//
////    @GetMapping("/blog/api/user/...")
////    @GetMapping("/{domain}/{인증}/{권한}/...")
////    @Ge
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
//}
