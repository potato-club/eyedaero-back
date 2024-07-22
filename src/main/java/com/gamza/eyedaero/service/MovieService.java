package com.gamza.eyedaero.service;

import com.gamza.eyedaero.dto.movie.MovieRequestDTO;
import com.gamza.eyedaero.dto.movie.MovieResponseDTO;
import com.gamza.eyedaero.entity.MovieEntity;
import com.gamza.eyedaero.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieResponseDTO> sendMovieInfo(String movieName) {
        List<MovieEntity> movieEntities = movieRepository.findByNameContaining(movieName);

        // 엔티티를 DTO로 변환하여 반환
        return movieEntities.stream()
                .map(movieEntity -> new MovieResponseDTO(movieEntity.getId(), movieEntity.getName()))
                .collect(Collectors.toList());
    }

}
