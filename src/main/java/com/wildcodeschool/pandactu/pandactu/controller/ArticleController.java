package com.wildcodeschool.pandactu.pandactu.controller;

import com.wildcodeschool.pandactu.pandactu.entity.Article;
import com.wildcodeschool.pandactu.pandactu.entity.Comment;
import com.wildcodeschool.pandactu.pandactu.repository.ArticleRepository;
import com.wildcodeschool.pandactu.pandactu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Controller
@RequestMapping(path = "/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/liste")
    private String getAllArticles(Model out) {
        out.addAttribute("articles", articleRepository.findAll());

        return "articles";
    }

    @PostMapping("/create")
    public String createArticle(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/accueil";
    }

    @PostMapping("/update/{id}")
    public String updateArticle(@ModelAttribute Article article, @PathVariable("id") Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article articleFromDb = optionalArticle.get();
            articleFromDb.setTitle(article.getTitle());
            articleFromDb.setPicture(article.getPicture());
            articleFromDb.setContent(article.getContent());
            articleFromDb.setSource(article.getSource());
            articleFromDb.setArticleId(articleId);
            articleRepository.save(articleFromDb);

        }
        return "redirect:/article/{id}";
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@ModelAttribute Article article, @PathVariable("id") Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article articleFromDb = optionalArticle.get();
            commentRepository.deleteAll(articleFromDb.getComments());
            articleRepository.delete(articleFromDb);
        }
        return "redirect:/accueil";
    }

    @GetMapping("/{id}")
    private String getArticleById(@PathVariable("id") Long articleId, Model out) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            out.addAttribute("article", article.get());
            Comment comment = new Comment();
            out.addAttribute("comment", comment);
            out.addAttribute("allComment", commentRepository.findAllByArticle_ArticleId(articleId));
            out.addAttribute("actualityId", articleId);
        }
        return "article";
    }
}
