package com.socialWork.forum.service.impl;


import com.socialWork.forum.entity.ArticleType;
import com.socialWork.forum.entity.repository.ArticleRepository;
import com.socialWork.forum.entity.repository.ArticleTypeRepository;
import com.socialWork.forum.service.ForumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {
    public ForumServiceImpl(ArticleTypeRepository articleTypeRepo, ArticleRepository articleRepo) {
        this.articleTypeRepo = articleTypeRepo;
        this.articleRepo = articleRepo;
    }

    private ArticleTypeRepository articleTypeRepo;
    private ArticleRepository articleRepo;

    @Override
    public List<ArticleType> getArticleTypeList() {
        return articleTypeRepo.findAll();
    }
}
