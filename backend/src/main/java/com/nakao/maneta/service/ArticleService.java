package com.nakao.maneta.service;

import com.nakao.maneta.dto.request.ArticleRequest;
import com.nakao.maneta.dto.response.ArticleResponse;
import com.nakao.maneta.entity.Article;
import com.nakao.maneta.entity.Tag;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.repository.ArticleRepository;
import com.nakao.maneta.repository.TagRepository;
import com.nakao.maneta.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public List<ArticleResponse> selectArticleAll() {
        return articleRepository.findAll().stream().map(article -> toResponse(article)).collect(Collectors.toList());
    }

    public ArticleResponse selectArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("記事が見つかりません"));
        return toResponse(article);
    }

    public ArticleResponse insertArticle(ArticleRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
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
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("記事が見つかりません"));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        if (!article.getUser().getUsername().equals(username)) {
            throw new RuntimeException("編集権限がありません");
        }
        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setPublished(request.isPublished());

        List<Tag> tags = toTagList(request.getTagNames());
        article.setTags(tags);
        return toResponse(articleRepository.save(article));
    }

    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("記事が見つかりません"));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        if (!article.getUser().getUsername().equals(username)) {
            throw new RuntimeException("削除権限がありません");
        }
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
