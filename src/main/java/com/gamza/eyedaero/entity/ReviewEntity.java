package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@RequiredArgsConstructor
@Table
@Getter
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_room_id")
    private TheaterRoomEntity theaterRoom;

    @Column(nullable = false)
    private int recommendations;

    @Column
    private String content;

    @Column
    private int rate;

//    public RecommendationEntity(UserEntity user, ReviewEntity review) {
//        this.user = user;
//        this.review = review;
//    }

}
