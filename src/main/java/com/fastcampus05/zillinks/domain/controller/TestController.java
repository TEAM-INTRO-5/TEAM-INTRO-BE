package com.fastcampus05.zillinks.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
}
