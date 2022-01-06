package com.example.sns.domain.image.domain;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.global.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_image")
public class Image extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imagePath;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Image updateImage(String imagePath, String imageUrl) {
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        return this;
    }
}
