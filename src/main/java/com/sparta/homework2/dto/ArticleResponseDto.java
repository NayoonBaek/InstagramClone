package com.sparta.homework2.dto;

import com.sparta.homework2.model.Comment;
import com.sparta.homework2.model.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ArticleResponseDto {
    private Long id;
    private String author;
    private String content;
    private String image;

    private List<Comment> comments;
    private int likesSize;

    private boolean isLike;

    private String date;


    public ArticleResponseDto(Long id, String author, String content,
                              int likesSize, String image, Date time) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.likesSize = likesSize;
        this.image = image;
        this.date = Time.calculateTime(time);
    }
}
