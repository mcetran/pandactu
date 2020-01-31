package com.wildcodeschool.pandactu.pandactu.controller;

import com.wildcodeschool.pandactu.pandactu.entity.Admin;
import com.wildcodeschool.pandactu.pandactu.entity.Article;
import com.wildcodeschool.pandactu.pandactu.entity.Comment;
import com.wildcodeschool.pandactu.pandactu.repository.ArticleRepository;
import com.wildcodeschool.pandactu.pandactu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(path = "/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/create/{articleId}")
    public String createComment(@ModelAttribute Comment comment, @PathVariable("articleId") Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            comment.setArticle(optionalArticle.get());
            commentRepository.save(comment);
        }
        return "redirect:/article/" + articleId;
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long commentId, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        if (admin == null) {
            //TODO: FORBIDDEN
        }
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment commentFromDb = optionalComment.get();
            commentRepository.delete(commentFromDb);
            return "redirect:/article/" + commentFromDb.getArticle().getArticleId();
        }
        return "redirect:/home"; //TODO: NOT FOUND
    }
}
