package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.movie.MovieRequestDTO;
import com.gamza.eyedaero.dto.user.LoginRequestDto;
import com.gamza.eyedaero.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping()
    public ResponseEntity<Long> send(@RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.send(movieRequestDTO));
    }

}
