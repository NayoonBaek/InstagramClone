package com.sparta.homework2.dto;

import com.sparta.homework2.model.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentRequestDto {
    private String comment;

//    public CommentRequestDto(String comment) {
//        this.comment = comment;
//    }
}
