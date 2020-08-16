package com.socialWork.socialWork.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @RequestMapping("/")
    public String hello() {
        log.info("output hello world");
        return "向全世界說聲Spring Boot 很高興認識你!我要崩潰惹QQ!!!";
    }

}
