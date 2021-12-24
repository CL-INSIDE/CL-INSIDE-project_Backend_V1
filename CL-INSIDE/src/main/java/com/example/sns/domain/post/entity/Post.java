package com.example.sns.domain.post.entity;

import com.example.sns.domain.auth.entity.user.User;
import com.example.sns.domain.image.entity.Image;
import com.example.sns.domain.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "post_tbl")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private Category category;

    private Integer likeCounts;

    private Integer hateCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Image image;

    public Post updatePost(String title, String content, Category category){
        this.title = title;
        this.content = content;
        this.category = category;
        return this;
    }

    public Post addPostLikeCounts(){
        this.likeCounts++;
        return this;
    }

    public Post removeLikeCounts(){
        this.likeCounts--;
        return this;
    }

    public Post addPostHateCounts(){
        this.hateCounts++;
        return this;
    }

    public Post removeHateCounts(){
        this.hateCounts--;
        return this;
    }
}
