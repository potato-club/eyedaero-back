package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.movie.MovieRequestDTO;
import com.gamza.eyedaero.dto.movie.MovieResponseDTO;
import com.gamza.eyedaero.dto.user.LoginRequestDto;
import com.gamza.eyedaero.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movie")
@Tag(name = "영화 Controller", description = "Review API")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/")
    @Operation(summary = "영화 검색")
    public ResponseEntity<List<MovieResponseDTO>> sendMovieInfo(@Parameter String name) {
        List<MovieResponseDTO> result = movieService.sendMovieInfo(name);
        return ResponseEntity.ok().body(result);
    }

}
