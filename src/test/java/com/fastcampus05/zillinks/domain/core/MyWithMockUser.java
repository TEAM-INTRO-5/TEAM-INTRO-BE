package com.fastcampus05.zillinks.domain.core;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MyWithMockUserFactory.class)
public @interface MyWithMockUser {
    long id() default 1L;
    String email() default "taeheoki@naver.com";
    String bizNum() default "2258701327";
    String role() default "USER";
}
