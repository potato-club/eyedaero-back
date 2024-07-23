package com.gamza.eyedaero.service;

import com.gamza.eyedaero.core.error.ErrorCode;
import com.gamza.eyedaero.core.error.exeption.NotFoundException;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomInfoResponseDto;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomListResponseDto;
import com.gamza.eyedaero.dto.theaterRoom.TheaterRoomSeatsReponseDto;
import com.gamza.eyedaero.entity.ReviewEntity;
import com.gamza.eyedaero.entity.SeatEntity;
import com.gamza.eyedaero.entity.TheaterEntity;
import com.gamza.eyedaero.entity.TheaterRoomEntity;
import com.gamza.eyedaero.repository.TheaterRepository;
import com.gamza.eyedaero.repository.TheaterRoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterRoomService {
    private final TheaterRoomRepository theaterRoomRepository;
    private final TheaterRepository theaterRepository;

    public TheaterRoomInfoResponseDto viewTheaterRoomInfo(Long theaterRoomId){
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

    public TheaterRoomListResponseDto viewTheaterRoomList(Long theatherId) {
        TheaterEntity theater = theaterRepository.findById(theatherId)
                .orElseThrow(() -> new NotFoundException("Theater not found", ErrorCode.NOT_FOUND_EXCEPTION));

        List<Long> theaterRoomIds = theater.getTheaterRooms().stream()
                .map(theaterRoomEntity -> theaterRoomEntity.getId())
                .collect(Collectors.toList());

        return new TheaterRoomListResponseDto(theater.getName(), theaterRoomIds);
    }

    public TheaterRoomSeatsReponseDto viewSeats(Long theaterRoomId){
        TheaterRoomEntity theaterRoom = theaterRoomRepository.findById(theaterRoomId)
                .orElseThrow(()-> new NotFoundException("TheaterRoom not found", ErrorCode.NOT_FOUND_EXCEPTION));


        List<String> seats = theaterRoom.getSeats().stream()
                .map(SeatEntity::getSeats)
                .collect(Collectors.toList());

        return new TheaterRoomSeatsReponseDto(theaterRoom.getId(), seats);
    }

}
