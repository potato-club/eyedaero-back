package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Table
@Getter
public class TheaterRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_room_id")
    private Long id;

    @OneToMany(mappedBy = "theaterRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater")
    private TheaterEntity theater;

    @OneToMany(mappedBy = "theaterRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieEntity> movies;

    @Column(nullable = false)
    private int seatCount;

    @Column(nullable = false)
    private float rate;

    @Column(nullable = false)
    private int ReviewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major")
    private AreaMajorEntity areaMajorEntity;


}
