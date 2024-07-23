package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomInfoResponseDto;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomListResponseDto;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomSeatsReponseDto;
import com.gamza.eyedaero.service.TheaterRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theaterroom")
@RequiredArgsConstructor
@Tag(name = "상영관 Controller", description = "Review API")
public class TheaterRoomController {

    private final TheaterRoomService theaterRoomService;

    @GetMapping("/info/{theaterRoomId}")
    @Operation(summary = "상영관 정보(리뷰 수, 별점)")
    public ResponseEntity<TheaterRoomInfoResponseDto> viewTheaterRoomInfo(@PathVariable Long theaterRoomId) {
        TheaterRoomInfoResponseDto result = theaterRoomService.viewTheaterRoomInfo(theaterRoomId);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/list/{theatherId}")
    @Operation(summary = "상영관 리스트 API")
    public ResponseEntity<TheaterRoomListResponseDto> viewTheaterRoomList(@PathVariable Long theatherId){
        TheaterRoomListResponseDto result = theaterRoomService.viewTheaterRoomList(theatherId);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/seats/{theaterRoomId}")
    @Operation(summary = "좌석 리스트 API")
    public ResponseEntity<TheaterRoomSeatsReponseDto> viewSeats(@PathVariable Long theaterRoomId){
        TheaterRoomSeatsReponseDto result = theaterRoomService.viewSeats(theaterRoomId);
        return ResponseEntity.ok(result);
    }
}
