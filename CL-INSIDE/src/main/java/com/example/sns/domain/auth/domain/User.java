package com.example.sns.domain.auth.domain;

import com.example.sns.domain.auth.domain.types.Role;
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

@Entity(name = "tbl_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
