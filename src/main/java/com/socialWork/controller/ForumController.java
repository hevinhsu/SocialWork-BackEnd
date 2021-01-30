package com.socialWork.controller;

import com.socialWork.forum.entity.ArticleType;
import com.socialWork.forum.service.ForumService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/forum")
@Api(tags = "文章註冊相關Controller")
@Slf4j
public class ForumController extends BaseController{


    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    private ForumService forumService;


    @RequestMapping(method = RequestMethod.GET, path = "/get/articleTypes")
    public List<ArticleType> getArticleTypes(){
        return forumService.getArticleTypeList();
    }
}
