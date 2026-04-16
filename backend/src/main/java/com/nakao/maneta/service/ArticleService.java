package com.nakao.maneta.service;

import com.nakao.maneta.dto.request.ArticleRequest;
import com.nakao.maneta.dto.response.ArticleResponse;
import com.nakao.maneta.entity.Article;
import com.nakao.maneta.entity.Tag;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.exception.ForbiddenException;
import com.nakao.maneta.exception.ResourceNotFoundException;
import com.nakao.maneta.repository.*;
import com.nakao.maneta.security.SecurityUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final SecurityUtil securityUtil;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository,SecurityUtil securityUtil, LikeRepository likeRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.securityUtil = securityUtil;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<ArticleResponse> selectArticleAll() {
        return articleRepository.findAll().stream().map(article -> toResponse(article)).collect(Collectors.toList());
    }

    public ArticleResponse selectArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("記事が見つかりません"));
        return toResponse(article);
    }

    public ArticleResponse insertArticle(ArticleRequest request) {
        User user = securityUtil.getCurrentUser();
        var article = new Article();
        article.setUser(user);
        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setPublished(request.isPublished());

        List<Tag> tags = toTagList(request.getTagNames());
        article.setTags(tags);
        return toResponse(articleRepository.save(article));
    }

    public ArticleResponse updateArticle(Long articleId, ArticleRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("記事が見つかりません"));
        User user = securityUtil.getCurrentUser();
        if (!article.getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("編集権限がありません");
        }
        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setPublished(request.isPublished());

        List<Tag> tags = toTagList(request.getTagNames());
        article.setTags(tags);
        return toResponse(articleRepository.save(article));
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("記事が見つかりません"));
        User user = securityUtil.getCurrentUser();
        if (!article.getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("削除権限がありません");
        }
        commentRepository.deleteByArticleId(articleId);
        likeRepository.deleteByArticleId(articleId);
        articleRepository.deleteById(articleId);
    }

    private ArticleResponse toResponse(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setBody(article.getBody());
        response.setPublished(article.isPublished());
        response.setCreatedAt(article.getCreatedAt());
        response.setUpdatedAt(article.getUpdatedAt());
        response.setAuthorUsername((article.getUser().getUsername()));
        response.setTagNames(article.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList()));
        response.setLikeCount((int) likeRepository.countByArticleId(article.getId()));
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername.equals("anonymousUser")){
            response.setLikedByMe(false);
        } else {
            User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new ResourceNotFoundException("ユーザーが見つかりません"));
            response.setLikedByMe(
                    likeRepository.existsByArticleIdAndUserId(article.getId(), currentUser.getId())
            );
        }
        return response;
    }

    public List<ArticleResponse> selectArticleByTag(String tagName) {
        return articleRepository.findByTagsName(tagName).stream().map(article -> toResponse(article)).collect(Collectors.toList());
    }

    private List<Tag> toTagList(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(name -> {
                            return tagRepository.findByName(name).orElseGet(() -> {
                                        var newtag = new Tag();
                                        newtag.setName(name);
                                        return tagRepository.save(newtag);
                                    }
                            );
                        }
                )
                .collect(Collectors.toList());
        return tags;
    }
}
