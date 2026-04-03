package com.nakao.maneta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class LikeId implements Serializable {
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "user_id")
    private Long userId;
}
