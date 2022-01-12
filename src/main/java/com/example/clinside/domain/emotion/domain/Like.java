package com.example.clinside.domain.emotion.domain;

import com.example.clinside.domain.auth.domain.User;
import com.example.clinside.domain.post.domain.Post;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Entity(name = "tbl_like")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Like(User user, Post post){
        this.user = user;
        this.post = post;
    }
}
