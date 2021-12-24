package com.example.sns.domain.auth.entity.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refresh_token")
public class RefreshToken implements Serializable {

    @Id
    private String name;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long refreshExpiration;

    public RefreshToken update(Long refreshExp){
        this.refreshExpiration = refreshExp;
        return this;
    }
}