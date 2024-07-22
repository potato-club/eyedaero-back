package com.gamza.eyedaero.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MovieResponseDTO {
    private long id;
    private String name;
}
