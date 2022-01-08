package com.example.sns.domain.post.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.emotion.domain.Hate;
import com.example.sns.domain.emotion.domain.Like;
import com.example.sns.domain.image.domain.Image;
import com.example.sns.global.domain.BaseTimeEntity;
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

    @Column(nullable = false, length = 500)
    private String content;

    private Integer likeCounts;
    private Integer hateCounts;

    private boolean isLike;
    private boolean isHate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Hate> hates = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne(mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Image image;

    //-------------------------------------------------------------------------------------------------------------------
    public Post updatePost(String title, String content){
        this.title = title;
        this.content = content;
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
