package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.review.ReviewCreateRequestDto;
import com.gamza.eyedaero.dto.review.ReviewListResponseDto;
import com.gamza.eyedaero.dto.review.ReviewResponseDto;
import com.gamza.eyedaero.service.ReviewService;
import com.gamza.eyedaero.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Tag(name = "리뷰 Controller", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(summary = "리뷰 작성")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewCreateRequestDto requestDto, HttpServletRequest request) {
        ReviewResponseDto result = reviewService.createReview(requestDto, request);
        return ResponseEntity.ok(result);

    }
    @GetMapping("/list/{theaterRoomId}")
    @Operation(summary = "리뷰 조회")
    public ResponseEntity<List<ReviewListResponseDto>> viewReviewList(@PathVariable Long theaterRoomId){
        List<ReviewListResponseDto> result = reviewService.viewReviewList(theaterRoomId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 추천")
    public ResponseEntity<String> reviewRecommend(@PathVariable Long reviewId, HttpServletRequest request){
        reviewService.reviewRecommend(reviewId, request);
        return ResponseEntity.ok().body("추천 완료");
    }

}
