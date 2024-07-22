package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.review.ReviewCreateRequestDto;
import com.gamza.eyedaero.dto.review.ReviewResponseDto;
import com.gamza.eyedaero.service.ReviewService;
import com.gamza.eyedaero.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Tag(name = "리뷰 Controller", description = "Review API")
public class ReviewController {
    //리뷰 만들기 - 별점 포함, 내용, 리뷰시간, 사용자id
    //리뷰 조회하기
    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(summary = "리뷰 작성")
    public ResponseEntity<ReviewResponseDto> login(@RequestBody ReviewCreateRequestDto requestDto, HttpServletRequest request) {
        ReviewResponseDto result = reviewService.createReview(requestDto, request);
        return ResponseEntity.ok(result);

    }

}
