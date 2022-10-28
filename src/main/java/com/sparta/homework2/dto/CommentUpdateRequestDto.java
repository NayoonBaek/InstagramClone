package com.sparta.homework2.dto;

import com.sparta.homework2.model.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentUpdateRequestDto {
    private String comment;

    public CommentUpdateRequestDto(Long id, String name, String comment) {
        this.comment = comment;
    }
}
