package com.gamza.eyedaero.service;

import com.gamza.eyedaero.dto.movie.MovieRequestDTO;
import com.gamza.eyedaero.entity.MovieEntity;
import com.gamza.eyedaero.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public long send(MovieRequestDTO movieRequestDTO) {
        MovieEntity movieEntity =  movieRepository.findByName(movieRequestDTO.getName());
        return movieEntity.getId();
    }

}
