package com.wildcodeschool.pandactu.pandactu.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Lob
    private String content;

    private Date date = new Date();
    private String title;
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private Admin author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REFRESH)
    private List<Comment> comments = new ArrayList<>();

    public Article() {
    }

    public Admin getAuthor() {
        return author;
    }

    public void setAuthor(Admin author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFormatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(getDate());
        return date;
    }
}
