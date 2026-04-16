package com.nakao.maneta.repository;

import com.nakao.maneta.entity.Like;
import com.nakao.maneta.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    long countByArticleId(Long articleId);
    boolean existsByArticleIdAndUserId(Long articleId, Long userId);
    void deleteByArticleId(Long articleId);
}
