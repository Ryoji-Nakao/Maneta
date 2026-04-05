package com.nakao.maneta.service;

import com.nakao.maneta.entity.Like;
import com.nakao.maneta.entity.LikeId;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.repository.ArticleRepository;
import com.nakao.maneta.repository.LikeRepository;
import com.nakao.maneta.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, ArticleRepository articleRepository, UserRepository userRepository){
        this.likeRepository = likeRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public void addLike(Long articleId){
        User user = getCurrentUser();
        LikeId likeId = makeLikeId(articleId,user);
        var like = new Like();
        like.setId(likeId);
        like.setArticle(articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("記事が見つかりません")));
        like.setUser(user);
        likeRepository.save(like);
    }

    public void removeLike(Long articleId){
        User user = getCurrentUser();
        LikeId likeId = makeLikeId(articleId,user);
        likeRepository.deleteById(likeId);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
    }

    private LikeId makeLikeId(Long articleId,User user){
        var likeId = new LikeId();
        likeId.setArticleId(articleId);
        likeId.setUserId(user.getId());
        return  likeId;
    }
}
