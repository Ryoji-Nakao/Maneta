package com.nakao.maneta.service;

import com.nakao.maneta.dto.request.CommentRequest;
import com.nakao.maneta.dto.response.CommentResponse;
import com.nakao.maneta.entity.Article;
import com.nakao.maneta.entity.Comment;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.repository.ArticleRepository;
import com.nakao.maneta.repository.CommentRepository;
import com.nakao.maneta.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<CommentResponse> selectCommentsByArticleId(Long articleId){
        return commentRepository.findByArticleId(articleId).stream().map(comment -> toResponse(comment)).collect(Collectors.toList());
    }

    public CommentResponse insertComment(Long articleId, CommentRequest request){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("記事が見つかりません"));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
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
