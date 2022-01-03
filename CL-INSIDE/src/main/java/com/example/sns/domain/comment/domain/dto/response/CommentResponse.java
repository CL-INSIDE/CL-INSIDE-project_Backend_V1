package com.example.sns.domain.comment.domain.dto.response;

import com.example.sns.domain.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 200, message = "댓글은 1자 이상 200자 이하입니다.")
    private String content;

    private UserInfo userInfo;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserInfo{
        private String name;
    }
}
