package com.sparta.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikeResponseDto {
    private String username;
    private Long articleId;

//    public LikeResponseDto(String username, Long articleId){
//        this.username = username;
//        this.articleId = articleId;
//    }
}
