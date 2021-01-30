package com.socialWork.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/forum")
@Api(tags = "文章註冊相關Controller")
@Slf4j
public class ForumController extends BaseController{

}
