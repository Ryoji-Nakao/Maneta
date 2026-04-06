package com.nakao.maneta.service;

import com.nakao.maneta.entity.Like;
import com.nakao.maneta.entity.LikeId;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.exception.ResourceNotFoundException;
import com.nakao.maneta.repository.ArticleRepository;
import com.nakao.maneta.repository.LikeRepository;
import com.nakao.maneta.security.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;
    private final SecurityUtil securityUtil;

    public LikeService(LikeRepository likeRepository, ArticleRepository articleRepository, SecurityUtil securityUtil){
        this.likeRepository = likeRepository;
        this.articleRepository = articleRepository;
        this.securityUtil = securityUtil;
    }

    public void addLike(Long articleId){
        User user = securityUtil.getCurrentUser();
        LikeId likeId = makeLikeId(articleId,user);
        var like = new Like();
        like.setId(likeId);
        like.setArticle(articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("記事が見つかりません")));
        like.setUser(user);
        likeRepository.save(like);
    }

    public void removeLike(Long articleId){
        User user = securityUtil.getCurrentUser();
        LikeId likeId = makeLikeId(articleId,user);
        likeRepository.deleteById(likeId);
    }

    private LikeId makeLikeId(Long articleId,User user){
        var likeId = new LikeId();
        likeId.setArticleId(articleId);
        likeId.setUserId(user.getId());
        return  likeId;
    }
}