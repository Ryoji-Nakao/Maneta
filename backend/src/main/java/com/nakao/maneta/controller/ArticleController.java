package com.nakao.maneta.controller;

import com.nakao.maneta.dto.request.ArticleRequest;
import com.nakao.maneta.dto.response.ArticleResponse;
import com.nakao.maneta.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<?> selectArticleAll(@RequestParam(required = false) String tag) {
        List<ArticleResponse> articles = tag != null
                ? articleService.selectArticleByTag(tag)
                : articleService.selectArticleAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> selectArticleDetail(@PathVariable Long articleId) {
        ArticleResponse article = articleService.selectArticleDetail(articleId);
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<?> insertArticle(@RequestBody ArticleRequest request) {
        ArticleResponse article = articleService.insertArticle(request);
        return ResponseEntity.status(201).body(article);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequest request) {
        ArticleResponse article = articleService.updateArticle(articleId, request);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}
