package com.nakao.maneta.controller;

import com.nakao.maneta.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("/{articleId}/likes")
    public ResponseEntity<?> addLike(@PathVariable Long articleId){
        likeService.addLike(articleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}/likes")
    public ResponseEntity<?> removeLike(@PathVariable Long articleId){
        likeService.removeLike(articleId);
        return ResponseEntity.noContent().build();
    }
}
