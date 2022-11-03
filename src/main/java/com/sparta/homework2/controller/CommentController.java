package com.sparta.homework2.controller;

import com.sparta.homework2.dto.CommentRequestDto;
import com.sparta.homework2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getComments(@PathVariable Long id) throws RuntimeException {
        try {
            return ResponseEntity.ok(commentService.getComments(id));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        }
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<?> createArticle(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) throws RuntimeException {
        return ResponseEntity.ok(commentService.createComment(id, requestDto));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateMemo(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        try {
            return ResponseEntity.ok(commentService.updateComment(id, requestDto));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteMemo(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok(id);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(404));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(403));
        }
    }
}
