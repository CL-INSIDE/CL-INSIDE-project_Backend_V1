package com.example.sns.domain.sympathy.entity;

import com.example.sns.domain.auth.entity.user.User;
import com.example.sns.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tbl_hate")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="hate_uk",
                        columnNames = {"post_id", "user_id"}
                )
        }
)
public class Hate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
