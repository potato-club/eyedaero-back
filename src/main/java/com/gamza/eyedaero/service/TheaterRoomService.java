package com.gamza.eyedaero.service;

import com.gamza.eyedaero.core.error.ErrorCode;
import com.gamza.eyedaero.core.error.exeption.NotFoundException;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomInfoResponseDto;
import com.gamza.eyedaero.entity.ReviewEntity;
import com.gamza.eyedaero.entity.TheaterRoomEntity;
import com.gamza.eyedaero.repository.TheaterRoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterRoomService {
    private final TheaterRoomRepository theaterRoomRepository;

    public TheaterRoomInfoResponseDto viewTheaterRoomInfo(Long theaterRoomId, HttpServletRequest request){
        TheaterRoomEntity theaterRoom = theaterRoomRepository.findById(theaterRoomId)
                .orElseThrow(() -> new NotFoundException("상영관을 찾을 수 없습니다", ErrorCode.NOT_FOUND_EXCEPTION));

        List<ReviewEntity> reviews = theaterRoom.getReviews();
        int reviewCount = reviews.size();
        float averageRate = (float) reviews.stream()
                .mapToDouble(ReviewEntity::getRate)
                .average()
                .orElse(0.0);

        return new TheaterRoomInfoResponseDto(theaterRoom.getTheater().getName(), theaterRoom.getId(), reviewCount, averageRate);

    }



}
