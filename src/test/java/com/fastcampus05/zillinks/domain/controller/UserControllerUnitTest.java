//package com.fastcampus05.zillinks.domain.controller;
//
//import com.fastcampus05.zillinks.core.advice.MyLogAdvice;
//import com.fastcampus05.zillinks.core.advice.MyValidAdvice;
//import com.fastcampus05.zillinks.core.auth.token.MyJwtProvider;
//import com.fastcampus05.zillinks.core.config.MyFilterRegisterConfig;
//import com.fastcampus05.zillinks.core.config.MySecurityConfig;
//import com.fastcampus05.zillinks.core.dummy.DummyEntity;
//import com.fastcampus05.zillinks.domain.core.MyWithMockUser;
//import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
//import com.fastcampus05.zillinks.domain.dto.user.UserRequest;
//import com.fastcampus05.zillinks.domain.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @WebMvcTest는 웹 계층 컴포넌트만 테스트로 가져옴
// */
//
//@ActiveProfiles("test")
//@EnableAspectJAutoProxy // AOP 활성화
//@Import({
//        MyValidAdvice.class,
//        MyLogAdvice.class,
//        MySecurityConfig.class,
//        MyFilterRegisterConfig.class
//}) // Advice 와 Security 설정 가져오기
//@WebMvcTest(
//        // 필요한 Controller 가져오기, 특정 필터를 제외하기
//        controllers = {UserController.class}
//)
//public class UserControllerUnitTest extends DummyEntity {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private ObjectMapper om;
//    @MockBean
//    private UserService userService;
//
//
//    @DisplayName("사업자등록번호 인증 테스트")
//    @Test
//    public void validBizNum_성공_test() throws Exception {
//        // given
//        UserRequest.BizNumInDTO bizNumInDTO = new UserRequest.BizNumInDTO();
//        bizNumInDTO.setBizNum("2258701327");
//
//        // when
//        ResultActions resultActions = mvc.perform(
//                post("/api/validBizNum")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(bizNumInDTO))
//        );
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
//    }
//
//    @DisplayName("사업자등록번호 인증 테스트")
//    @Test
//    public void validBizNum_유효성검사_실패1_test() throws Exception {
//        // given
//        UserRequest.BizNumInDTO bizNumInDTO = new UserRequest.BizNumInDTO();
//        bizNumInDTO.setBizNum("2258701327999");
////        bizNumInDTO.setBizNum("2258701327");
//
//        // when
//        ResultActions resultActions = mvc.perform(
//                post("/api/validBizNum")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(bizNumInDTO))
//        );
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("badRequest"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.key").value("bizNum"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.value").value("숫자 10자리로 입력해 주세요."));
//    }
//
//    @DisplayName("사업자등록번호 인증 테스트")
//    @Test
//    public void validBizNum_유효성검사_실패2_test() throws Exception {
//        // given
//        UserRequest.BizNumInDTO bizNumInDTO = new UserRequest.BizNumInDTO();
//        bizNumInDTO.setBizNum("225-87-01327");
////        bizNumInDTO.setBizNum("2258701327");
//
//        // when
//        ResultActions resultActions = mvc.perform(
//                post("/api/validBizNum")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(bizNumInDTO))
//        );
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("badRequest"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.key").value("bizNum"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.value").value("숫자 10자리로 입력해 주세요."));
//    }
////
////    @DisplayName("사업자등록번호 인증 테스트")
////    @Test
////    public void validBizNum_데이터없을때_실패_test() throws Exception {
////        // given
////        UserRequest.BizNumInDTO bizNumInDTO = new UserRequest.BizNumInDTO();
////        bizNumInDTO.setBizNum("1111111111");
//////        bizNumInDTO.setBizNum("2258701327");
////
////        Mockito.when(userService.validBizNum(bizNumInDTO.getBizNum()))
////                .thenReturn(String.valueOf(new ResponseDTO(HttpStatus.BAD_REQUEST, "badRequest", "등록되지 않은 번호입니다.")));
////
////        // when
////        ResultActions resultActions = mvc.perform(
////                post("/api/validBizNum")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(om.writeValueAsString(bizNumInDTO))
////        );
////
////        // then
////        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
////        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
////        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("badRequest"));
////    }
//
//    @Test
//    public void join_test() throws Exception {
//        // 준비
//        UserRequest.JoinInDTO joinInDTO = new UserRequest.JoinInDTO();
//        joinInDTO.setUsername("cos");
//        joinInDTO.setPassword("1234");
//        joinInDTO.setEmail("cos@nate.com");
//        joinInDTO.setFullName("코스");
//        String requestBody = om.writeValueAsString(joinInDTO);
//
//        // 가정해볼께
//        User cos = newMockUser(1L,"cos", "코스");
//        UserResponse.JoinOutDTO joinOutDTO = new UserResponse.JoinOutDTO(cos);
//        Mockito.when(userService.회원가입(any())).thenReturn(joinOutDTO);
//
//        // 테스트진행
//        ResultActions resultActions = mvc
//                .perform(post("/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : " + responseBody);
//
//        // 검증해볼께
//        resultActions.andExpect(jsonPath("$.data.id").value(1L));
//        resultActions.andExpect(jsonPath("$.data.username").value("cos"));
//        resultActions.andExpect(jsonPath("$.data.fullName").value("코스"));
//        resultActions.andExpect(status().isOk());
//    }
//
//    @Test
//    public void login_test() throws Exception {
//        // given
//        UserRequest.LoginInDTO loginInDTO = new UserRequest.LoginInDTO();
//        loginInDTO.setUsername("cos");
//        loginInDTO.setPassword("1234");
//        String requestBody = om.writeValueAsString(loginInDTO);
//
//        // stub
//        Mockito.when(userService.로그인(any())).thenReturn("Bearer 1234");
//
//        // when
//        ResultActions resultActions = mvc
//                .perform(post("/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : " + responseBody);
//
//        // then
//        String jwtToken = resultActions.andReturn().getResponse().getHeader(MyJwtProvider.HEADER);
//        Assertions.assertThat(jwtToken.startsWith(MyJwtProvider.TOKEN_PREFIX)).isTrue();
//        resultActions.andExpect(status().isOk());
//    }
//
//    @MyWithMockUser(id = 1L, username = "cos", role = "USER", fullName = "코스")
//    //@WithMockUser(value = "ssar", password = "1234", roles = "USER")
//    @Test
//    public void detail_test() throws Exception {
//        // given
//        Long id = 1L;
//
//        // stub
//        User cos = newMockUser(1L,"cos", "코스");
//        UserResponse.DetailOutDTO detailOutDTO = new UserResponse.DetailOutDTO(cos);
//        Mockito.when(userService.회원상세보기(any())).thenReturn(detailOutDTO);
//
//        // when
//        ResultActions resultActions = mvc
//                .perform(get("/s/user/"+id));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : " + responseBody);
//
//        // then
//        resultActions.andExpect(jsonPath("$.data.id").value(1L));
//        resultActions.andExpect(jsonPath("$.data.username").value("cos"));
//        resultActions.andExpect(jsonPath("$.data.email").value("cos@nate.com"));
//        resultActions.andExpect(jsonPath("$.data.fullName").value("코스"));
//        resultActions.andExpect(jsonPath("$.data.role").value("USER"));
//        resultActions.andExpect(status().isOk());
//    }
//}
