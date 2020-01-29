package com.wildcodeschool.pandactu.pandactu.repository;

import com.wildcodeschool.pandactu.pandactu.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticle_ArticleId(Long actualityId);
}
