package com.gamza.eyedaero.service;

import com.gamza.eyedaero.dto.review.ReviewCreateRequestDto;
import com.gamza.eyedaero.dto.review.ReviewResponseDto;
import com.gamza.eyedaero.entity.ReviewEntity;
import com.gamza.eyedaero.entity.TheaterRoomEntity;
import com.gamza.eyedaero.entity.UserEntity;
import com.gamza.eyedaero.repository.ReviewRepository;
import com.gamza.eyedaero.repository.TheaterRoomRepository;
import com.gamza.eyedaero.repository.UserRepository;
import com.gamza.eyedaero.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TheaterRoomRepository theaterRoomRepository;


    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto, HttpServletRequest request){
        String token = jwtTokenProvider.resolveAccessToken(request);
        Long userId = jwtTokenProvider.extractId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TheaterRoomEntity theaterRoom = theaterRoomRepository.findById(requestDto.getTheaterRoomId())
                .orElseThrow(() -> new RuntimeException("Theater room not found"));

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .theaterRoom(theaterRoom)
                .content(requestDto.getContents())
                .rate(requestDto.getRate())
                .recommendations(0)
                .createAt(LocalDateTime.now())
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .reviewId(savedReview.getId())
                .contents(savedReview.getContent())
                .recommendCount(savedReview.getRecommendations())
                .createAt(savedReview.getCreateAt())
                .rate(savedReview.getRate())
                .build();


    }



}
