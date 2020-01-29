package com.wildcodeschool.pandactu.pandactu.controller;

import com.wildcodeschool.pandactu.pandactu.entity.Article;
import com.wildcodeschool.pandactu.pandactu.entity.Comment;
import com.wildcodeschool.pandactu.pandactu.repository.ArticleRepository;
import com.wildcodeschool.pandactu.pandactu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/articles")
    private String getAllArticles(Model out) {
        out.addAttribute("articles", articleRepository.findAll());

        return "articles";
    }

    @GetMapping("/article/{id}")
    private String getArticleById(@PathVariable("id") Long articleId, Model out) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            out.addAttribute("article", article.get());
            Comment comment = new Comment();
            out.addAttribute("comment", comment);
            out.addAttribute("allComment", commentRepository.findAllByArticle_ArticleId(articleId));
            out.addAttribute("actualityId", articleId);

            return "actuality";
        }
        return null;
    }
}
