package com.gamza.eyedaero.dto.theaterRoom;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TheaterRoomInfoResponseDto {
    private final String TheaterName;
    private final Long TheaterRoomName;
    private final int reviewCount;
    private final float rate;
}
