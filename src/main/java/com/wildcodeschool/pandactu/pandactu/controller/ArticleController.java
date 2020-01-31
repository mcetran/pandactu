package com.wildcodeschool.pandactu.pandactu.controller;

import com.wildcodeschool.pandactu.pandactu.entity.Admin;
import com.wildcodeschool.pandactu.pandactu.entity.Article;
import com.wildcodeschool.pandactu.pandactu.entity.Comment;
import com.wildcodeschool.pandactu.pandactu.repository.ArticleRepository;
import com.wildcodeschool.pandactu.pandactu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(path = "/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/create")
    public String createArticle(@ModelAttribute Article article, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        if (admin == null) {
            //TODO: FORBIDDEN => return template erreur
        }
        article.setAuthor(admin);
        Article savedArticle = articleRepository.save(article);
        return "redirect:/article/" + savedArticle.getArticleId();
    }

    @PostMapping("/update/{id}")
    public String updateArticle(@ModelAttribute Article article, @PathVariable("id") Long articleId, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        if (admin == null) {
            //TODO: FORBIDDEN
        }
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article articleFromDb = optionalArticle.get();
            articleFromDb.setTitle(article.getTitle());
            articleFromDb.setPicture(article.getPicture());
            articleFromDb.setContent(article.getContent());
            articleFromDb.setArticleId(articleId);
            articleRepository.save(articleFromDb);
        }
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@ModelAttribute Article article, @PathVariable("id") Long articleId, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        if (admin == null) {
            //TODO: FORBIDDEN
        }
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article articleFromDb = optionalArticle.get();
            commentRepository.deleteAll(articleFromDb.getComments());
            articleRepository.delete(articleFromDb);
        }
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    private String getArticleById(@PathVariable("id") Long articleId, Model out, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        out.addAttribute("isAdmin", admin != null);
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            out.addAttribute("article", article.get());
            out.addAttribute("comment", new Comment());
        }
        return "article";
    }
}
