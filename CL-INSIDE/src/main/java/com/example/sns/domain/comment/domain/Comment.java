package com.example.sns.domain.comment.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.global.domain.BaseTimeEntity;
import com.example.sns.domain.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbl_comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content, User user, LocalDateTime createdDate, LocalDateTime updatedDate, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
