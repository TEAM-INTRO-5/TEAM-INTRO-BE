//package com.fastcampus05.zillinks.domain.core;
//
//import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
//import com.fastcampus05.zillinks.domain.model.user.User;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//import java.time.LocalDateTime;
//
//public class MyWithMockUserFactory implements WithSecurityContextFactory<MyWithMockUser> {
//    @Override
//    public SecurityContext createSecurityContext(MyWithMockUser mockUser) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        User user = User.builder()
//                .id(mockUser.id())
//                .username(mockUser.username())
//                .password("1234")
//                .email(mockUser.username()+"@nate.com")
//                .fullName(mockUser.fullName())
//                .role("USER")
//                .status(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//        MyUserDetails myUserDetails = new MyUserDetails(user);
//        Authentication auth = new UsernamePasswordAuthenticationToken(myUserDetails, myUserDetails.getPassword(), myUserDetails.getAuthorities());
//        context.setAuthentication(auth);
//        return context;
//    }
//}
