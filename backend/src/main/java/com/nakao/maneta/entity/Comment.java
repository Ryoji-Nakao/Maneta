package com.nakao.maneta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;
}
