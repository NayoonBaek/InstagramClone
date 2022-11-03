package com.sparta.homework2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.homework2.dto.ArticleResponseDto;
import com.sparta.homework2.dto.request.ContentRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Article extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false)
//    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column
    private String image;

    @JsonIgnore
    private Date time = new Date();

    @JsonIgnore
    private String date;

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public Article(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Article(String username, ContentRequestDto contentRequestDto, String s3FileName, String time) {
        this.author = username;
        this.content = contentRequestDto.getContent();
        this.image = s3FileName;
        this.date = time;
    }
    public Article(String username, String content, String imgPath) {
        this.author = username;
        this.content = content;
        this.image = imgPath;
    }



    public void update(String username, ContentRequestDto contentRequestDto) {
        this.author = username;
        this.content = contentRequestDto.getContent();
    }

    public ArticleResponseDto toDto() {
        int likseSize = this.likes.size();
        return new ArticleResponseDto(this.id, this.author, this.content, likseSize, this.image, this.time);
    }
}
