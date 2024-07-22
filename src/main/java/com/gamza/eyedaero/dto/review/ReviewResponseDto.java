package com.gamza.eyedaero.dto.review;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponseDto {
    private final Long reviewId;
    private final String contents;
    private final int recommendCount;
    private final LocalDateTime createAt;
    private final float rate;
}
