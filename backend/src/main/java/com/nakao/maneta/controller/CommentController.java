package com.nakao.maneta.controller;

import com.nakao.maneta.dto.request.CommentRequest;
import com.nakao.maneta.dto.response.CommentResponse;
import com.nakao.maneta.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<?> selectCommentsByArticleId(@PathVariable Long articleId){
        List<CommentResponse> comments =commentService.selectCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<?> insertComment(@PathVariable Long articleId, @RequestBody CommentRequest request){
        CommentResponse comment = commentService.insertComment(articleId, request);
        return ResponseEntity.ok(comment);
    }
}
