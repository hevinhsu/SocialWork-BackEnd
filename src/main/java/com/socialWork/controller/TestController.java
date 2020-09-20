package com.socialWork.controller;



import com.socialWork.user.pojo.User;
import com.socialWork.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public String hello() {
        log.info("output hello world");
        return "向全世界說聲Spring Boot 很高興認識你!我要崩潰惹QQ!!!";
    }

    @GetMapping("/test")
    public String hello1() {
        log.info("output hello world-1");

        return "向全世界說聲Spring Boot 很高興認識你!test1!!!";



    }

}
