package com.nakao.maneta.service;

import com.nakao.maneta.dto.request.CommentRequest;
import com.nakao.maneta.dto.response.CommentResponse;
import com.nakao.maneta.entity.Article;
import com.nakao.maneta.entity.Comment;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.exception.ResourceNotFoundException;
import com.nakao.maneta.repository.ArticleRepository;
import com.nakao.maneta.repository.CommentRepository;
import com.nakao.maneta.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final SecurityUtil securityUtil;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, SecurityUtil securityUtil){
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.securityUtil = securityUtil;
    }

    public List<CommentResponse> selectCommentsByArticleId(Long articleId){
        return commentRepository.findByArticleId(articleId).stream().map(comment -> toResponse(comment)).collect(Collectors.toList());
    }

    public CommentResponse insertComment(Long articleId, CommentRequest request){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("記事が見つかりません"));
        User user = securityUtil.getCurrentUser();
        var comment = new Comment();
        comment.setBody(request.getBody());
        comment.setArticle(article);
        comment.setUser(user);
        return toResponse(commentRepository.save(comment));
    }

    private CommentResponse toResponse(Comment comment){
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setBody(comment.getBody());
        response.setCreatedAt(comment.getCreatedAt());
        response.setAuthorUsername(comment.getUser().getUsername());
        return response;
    }
}
