package com.example.sns.domain.auth.domain;

import com.example.sns.domain.auth.domain.types.Role;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.comment.domain.dto.response.CommentResponse;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "tbl_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"password", "role"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @Column(nullable = false, unique = true, length = 36)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 12)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    private Integer everyLikeCounts;
    @JsonIgnore
    private Integer everyHateCounts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    public User addLikeCounts(){
        this.everyLikeCounts++;
        return this;
    }

    public User userList(Integer id, String name){
        this.id = id;
        this.name = name;
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
