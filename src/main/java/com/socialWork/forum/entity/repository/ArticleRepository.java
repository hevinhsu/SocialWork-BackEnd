package com.socialWork.forum.entity.repository;

import com.socialWork.forum.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
