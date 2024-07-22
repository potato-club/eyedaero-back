package com.gamza.eyedaero.service;

import com.gamza.eyedaero.core.error.ErrorCode;
import com.gamza.eyedaero.core.error.exeption.NotFoundException;
import com.gamza.eyedaero.dto.review.ReviewCreateRequestDto;
import com.gamza.eyedaero.dto.review.ReviewListResponseDto;
import com.gamza.eyedaero.dto.review.ReviewResponseDto;
import com.gamza.eyedaero.entity.RecommendEntity;
import com.gamza.eyedaero.entity.ReviewEntity;
import com.gamza.eyedaero.entity.TheaterRoomEntity;
import com.gamza.eyedaero.entity.UserEntity;
import com.gamza.eyedaero.repository.RecommendRepository;
import com.gamza.eyedaero.repository.ReviewRepository;
import com.gamza.eyedaero.repository.TheaterRoomRepository;
import com.gamza.eyedaero.repository.UserRepository;
import com.gamza.eyedaero.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TheaterRoomRepository theaterRoomRepository;
    private final RecommendRepository recommendRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        Long userId = jwtTokenProvider.extractId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.NOT_FOUND_EXCEPTION));

        TheaterRoomEntity theaterRoom = theaterRoomRepository.findById(requestDto.getTheaterRoomId())
                .orElseThrow(() -> new NotFoundException("Theater room not found", ErrorCode.NOT_FOUND_EXCEPTION));

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .theaterRoom(theaterRoom)
                .content(requestDto.getContents())
                .rate(requestDto.getRate())
                .recommendations(0)
                .createAt(LocalDateTime.now())
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);

        theaterRoom.incrementReviewCount();
        theaterRoomRepository.save(theaterRoom);

        return ReviewResponseDto.builder()
                .reviewId(savedReview.getId())
                .userEmail(savedReview.getUser().getEmail())
                .contents(savedReview.getContent())
                .recommendCount(savedReview.getRecommendations())
                .createAt(savedReview.getCreateAt())
                .rate(savedReview.getRate())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> viewReviewList(Long theaterRoomId) {
        TheaterRoomEntity theaterRoom = theaterRoomRepository.findById(theaterRoomId)
                .orElseThrow(() -> new NotFoundException("해당 상영관이 없습니다.", ErrorCode.NOT_FOUND_EXCEPTION));

        return theaterRoom.getReviews().stream()
                .sorted(Comparator.comparing(ReviewEntity::getCreateAt).reversed())
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void reviewRecommend(Long reviewId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        Long userId = jwtTokenProvider.extractId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.NOT_FOUND_EXCEPTION));
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found", ErrorCode.NOT_FOUND_EXCEPTION));

        Optional<RecommendEntity> recommendEntityOptional = recommendRepository.findByUserAndReview(user, review);

        if (recommendEntityOptional.isPresent()) {
            RecommendEntity recommendEntity = recommendEntityOptional.get();
            recommendRepository.delete(recommendEntity);
            review.setRecommendations(review.getRecommendations() - 1);
        } else {
            RecommendEntity recommend = new RecommendEntity(user, review);
            recommendRepository.save(recommend);
            review.setRecommendations(review.getRecommendations() + 1);
        }

        reviewRepository.save(review);
    }
}
