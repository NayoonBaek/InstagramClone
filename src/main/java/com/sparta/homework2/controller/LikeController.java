package com.sparta.homework2.controller;


import com.sparta.homework2.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeservice;

    @PostMapping("/like/{id}")
    public ResponseEntity<?> createLike(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(likeservice.createLike(id));
    }
    @DeleteMapping("/like/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(likeservice.deleteLike(id));
    }

//    @GetMapping("/api/like/{memberId}")
//    public ResponseEntity<?> getArticleWithLikes(@PathVariable Long memberId)
//            throws SQLException {
//        try {
//            return ResponseEntity.ok(likeservice.getArticleWithLikes(memberId));
//        } catch (NullPointerException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
//        }
//    }
}
