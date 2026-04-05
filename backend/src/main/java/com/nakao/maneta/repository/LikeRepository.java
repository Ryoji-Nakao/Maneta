package com.nakao.maneta.repository;

import com.nakao.maneta.entity.Like;
import com.nakao.maneta.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

}
