package com.example.sns.domain.comment.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.global.domain.BaseTimeEntity;
import com.example.sns.domain.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tbl_comment")
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"post", "user"})
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<User> users;

    @Builder
    public Comment(String content, User user, LocalDateTime createdDate, LocalDateTime updatedDate, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
