package com.example.clinside.domain.auth.domain;

import com.example.clinside.domain.auth.domain.types.Role;
import com.example.clinside.domain.comment.domain.Comment;
import com.example.clinside.domain.emotion.domain.Like;
import com.example.clinside.domain.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"email", "password", "role"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 36)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 12)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer everyLikeCounts;
    private Integer everyHateCounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    //유저정보 여러게 가져오기 오류 고치기~! - 해결

    //사진 유저 권한 오류 고치기 - 보류 <- 🐛
    //좋아요 싫어요 기능 만들기 - 해결

    public User addLikeCounts() {
        this.everyLikeCounts++;
        return this;
    }

    public User removeLikeCounts() {
        this.everyLikeCounts--;
        return this;
    }

    public User addHateCounts() {
        this.everyHateCounts++;
        return this;
    }

    public User removeHateCounts(){
        this.everyHateCounts--;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
