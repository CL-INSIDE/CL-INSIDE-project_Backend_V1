package com.example.sns.domain.post.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.emotion.domain.Hate;
import com.example.sns.domain.emotion.domain.Like;
import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.example.sns.global.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tbl_post")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String title;

    @JsonIgnore
    @Column(nullable = false, length = 500)
    private String content;

    private Integer likeCounts;
    @JsonIgnore
    private Integer hateCounts;

    @JsonIgnore
    private boolean isLike;

    @JsonIgnore
    private boolean isHate;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Hate> hates = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Post updatePost(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }

    public Post postList(Integer id, String title){
        this.id = id;
        this.title = title;
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

    public Post addHateCounts(){
        this.hateCounts++;
        return this;
    }

    public Post removeHateCounts(){
        this.hateCounts--;
        return this;
    }
}
