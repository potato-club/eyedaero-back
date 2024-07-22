package com.gamza.eyedaero.dto.review;

import lombok.Data;

@Data
public class ReviewCreateRequestDto {
    private Long theaterRoomId;
    private String contents;
    private float rate;
}
