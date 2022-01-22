package com.example.clinside.domain.image.domain;

import com.example.clinside.domain.auth.domain.User;
import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_image")
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imagePath;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
