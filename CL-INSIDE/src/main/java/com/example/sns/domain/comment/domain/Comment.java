package com.example.sns.domain.comment.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.post.domain.BaseTimeEntity;
import com.example.sns.domain.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity(name = "tbl_comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, String content, User user) {
        this.post = post;
        this.content = content;
        this.user = user;
    }
}
