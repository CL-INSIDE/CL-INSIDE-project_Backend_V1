package com.example.sns.domain.auth.domain;

import com.example.sns.domain.auth.domain.types.Role;
import com.example.sns.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tbl_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 36)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 12)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer everyLikeCounts;
    private Integer everyHateCounts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public User addLikeCounts(){
        this.everyLikeCounts++;
        return this;
    }

    public User removeLikeCounts(){
        this.everyLikeCounts--;
        return this;
    }

    public User addHateCounts(){
        this.everyHateCounts++;
        return this;
    }

    public User removeHateCounts(){
        this.everyHateCounts--;
        return this;
    }
}
