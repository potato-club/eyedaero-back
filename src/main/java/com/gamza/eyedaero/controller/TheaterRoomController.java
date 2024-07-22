package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.review.ReviewCreateRequestDto;
import com.gamza.eyedaero.dto.review.ReviewResponseDto;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomInfoResponseDto;
import com.gamza.eyedaero.service.ReviewService;
import com.gamza.eyedaero.service.TheaterRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theaterroom")
@RequiredArgsConstructor
@Tag(name = "상영관 Controller", description = "Review API")
public class TheaterRoomController {

    private final TheaterRoomService theaterRoomService;

    @PostMapping("/info/{theaterRoomId}")
    @Operation(summary = "상영관 정보(리뷰 수, 별점)")
    public ResponseEntity<TheaterRoomInfoResponseDto> viewTheaterRoomInfo(@PathVariable Long theaterRoomId, HttpServletRequest request) {
        TheaterRoomInfoResponseDto result = theaterRoomService.viewTheaterRoomInfo(theaterRoomId, request);
        return ResponseEntity.ok(result);

    }
}
