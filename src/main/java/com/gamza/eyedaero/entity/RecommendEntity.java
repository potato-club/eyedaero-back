package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Table
@Getter
public class RecommendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    public RecommendEntity(UserEntity user, ReviewEntity review) {
        this.user = user;
        this.review = review;
    }
}
