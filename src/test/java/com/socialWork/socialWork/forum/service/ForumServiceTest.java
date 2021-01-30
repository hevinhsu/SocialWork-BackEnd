package com.socialWork.socialWork.forum.service;

import com.socialWork.forum.entity.ArticleType;
import com.socialWork.forum.entity.repository.ArticleTypeRepository;
import com.socialWork.forum.service.ForumService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@DisplayName("測試論壇功能")
public class ForumServiceTest {

    @Autowired
    private ForumService forumService;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    @Test
    void getArticleType(){
        List<ArticleType> list = new ArrayList<>();
        list = forumService.getArticleTypeList();
        Assertions.assertEquals(list.size(), 2);

    }

}
