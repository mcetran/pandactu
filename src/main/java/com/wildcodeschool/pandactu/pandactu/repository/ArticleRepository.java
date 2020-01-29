package com.wildcodeschool.pandactu.pandactu.repository;

import com.wildcodeschool.pandactu.pandactu.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
