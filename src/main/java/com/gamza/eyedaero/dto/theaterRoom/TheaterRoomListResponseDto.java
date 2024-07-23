package com.gamza.eyedaero.dto.theaterRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TheaterRoomListResponseDto {
    private String theaterName;
    private List<Long> theaterRoomIds;

}
