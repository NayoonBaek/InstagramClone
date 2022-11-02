package com.sparta.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleRequestDto {

    private String content;

    private List<MultipartFile> image;


//    public ArticleRequestDto(String title, String content, String singer, String song) {
//        this.title = title;
//        this.content = content;
//        this.singer = singer;
//        this.song = song;
//    }
}
