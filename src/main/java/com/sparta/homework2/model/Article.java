package com.sparta.homework2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.homework2.dto.ArticleResponseDto;
import com.sparta.homework2.dto.request.ContentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

//    @Column(nullable = false)
//    private String singer;
//
//    @Column(nullable = false)
//    private String song;

    @Column
    private String image;

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

    public Article(String username, ContentRequestDto contentRequestDto, String s3FileName) {
        this.author = username;
        this.content = contentRequestDto.getContent();
        this.image = s3FileName;
    }

    public void update(String username, ContentRequestDto contentRequestDto) {
        this.author = username;
        this.content = contentRequestDto.getContent();
    }

    public ArticleResponseDto toDto() {
        int likseSize = this.likes.size();
        return new ArticleResponseDto(this.id, this.author, this.content, likseSize, this.image);
    }
}
