package com.socialWork.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialWork.auth.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "測試用Controller")
public class TestController {

	@Autowired
	UserRepository userRepo;

    @GetMapping("/")
    @ApiOperation(notes = "無身分，無參數", value = "")
    public String hello() {
        log.info("output hello world");
        return "向全世界說聲Spring Boot 很高興認識你!我要崩潰惹QQ!!!";
    }

    @GetMapping("/test")
    @ApiOperation(notes = "USER身分，無參數", value = "")
    public String hello1() {
        log.info("output hello world-1");

        return "向全世界說聲Spring Boot 很高興認識你!test1!!!";
    }
    
    @GetMapping("/auth/test")
    @ApiOperation(notes = "ADMIN身分，無參數", value = "")
    public String hello2() {
    	log.info("test admin role");
    	
    	return "test admin role";
    }
}
