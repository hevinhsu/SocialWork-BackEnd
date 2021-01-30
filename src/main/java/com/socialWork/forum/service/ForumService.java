package com.socialWork.forum.service;

import com.socialWork.forum.entity.ArticleType;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ForumService {
    List<ArticleType> getArticleTypeList();
}
