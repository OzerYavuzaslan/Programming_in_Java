package com.ozeryavuzaslan.webservices.restfulwebservicesProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts",
        indexes = {
                @Index(name = "user_id_index", columnList = "user_id"),
                @Index(name = "description_index", columnList = "title"),
                @Index(name = "composite_index1", columnList = "user_id, title"),
                @Index(name = "post_adddate_index", columnList = "adddate"),
                @Index(name = "post_upddate_index", columnList = "upddate")
        }
)
public class Post {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String post;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adddate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime upddate;

    public LocalDateTime getAdddate() {
        return adddate;
    }

    public void setAdddate(LocalDateTime adddate) {
        this.adddate = adddate;
    }

    public LocalDateTime getUpddate() {
        return upddate;
    }

    public void setUpddate(LocalDateTime upddate) {
        this.upddate = upddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
