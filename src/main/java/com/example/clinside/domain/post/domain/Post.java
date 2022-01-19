package com.example.clinside.domain.post.domain;

import com.example.clinside.domain.auth.domain.User;
import com.example.clinside.domain.comment.domain.Comment;
import com.example.clinside.domain.emotion.domain.Hate;
import com.example.clinside.domain.emotion.domain.Like;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.post.domain.types.Category;
import com.example.clinside.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    private Integer likeCounts;
    private Integer hateCounts;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isLike;
    private boolean isHate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private final Set<Like> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private final Set<Hate> hates = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne(mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Image image;

    public Post updatePost(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
        return this;
    }

    public Post addPostLikeCounts() {
        this.likeCounts++;
        return this;
    }

    public Post removeLikeCounts() {
        this.likeCounts--;
        return this;
    }

    public Post addPostHateCounts() {
        this.hateCounts++;
        return this;
    }

    public Post removeHateCounts() {
        this.hateCounts--;
        return this;
    }
}
