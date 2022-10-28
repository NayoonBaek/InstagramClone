package com.sparta.homework2.dto;

import com.sparta.homework2.model.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String comment;

    public CommentResponseDto(Long id, String nickname, String comment) {
        this.id = id;
        this.nickname = nickname;
        this.comment = comment;
    }
}
