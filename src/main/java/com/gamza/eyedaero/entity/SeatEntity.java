package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Table
@Getter
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column
    private Boolean kind; //좌석0 , 1, null

    @Column
    private String seats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theaterRoom")
    private TheaterRoomEntity theaterRoom;

}
