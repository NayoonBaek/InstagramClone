package com.sparta.homework2.controller;

import com.sparta.homework2.dto.request.ContentRequestDto;
import com.sparta.homework2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<?> getArticles() throws SQLException {
        return ResponseEntity.ok(articleService.getArticles());
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping(value = "/article", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createArticle(@RequestParam (value = "content") ContentRequestDto contentRequestDto,
                                           @RequestPart (value = "file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(articleService.createArticle(contentRequestDto, multipartFile));
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok(id);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    @PutMapping(value = "/article/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id,
                                           @RequestBody ContentRequestDto contentRequestDto
                                           ) throws IOException {
        try {
            return ResponseEntity.ok(articleService.updateArticle(id, contentRequestDto));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }
}
