package com.gamza.eyedaero.dto.theaterRoom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TheaterRoomSeatsReponseDto {
    private Long theaterRoomId;
    private List<String> seats;

}
