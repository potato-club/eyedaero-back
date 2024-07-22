package com.gamza.eyedaero.dto.review;

import com.gamza.eyedaero.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ReviewListResponseDto {
    private final Long reviewId;
    private final String contents;
    private final int recommendCount;
    private final LocalDateTime createAt;
    private final float rate;

    public ReviewListResponseDto(ReviewEntity entity) {
        this.reviewId = entity.getId();
        this.contents = entity.getContent();
        this.recommendCount = entity.getRecommendations();
        this.createAt = entity.getCreateAt();
        this.rate = entity.getRate();
    }
}