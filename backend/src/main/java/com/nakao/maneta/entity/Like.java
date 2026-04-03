package com.nakao.maneta.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    @EmbeddedId
    private LikeId id;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
