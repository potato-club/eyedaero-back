package com.gamza.eyedaero.dto.area.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AreaResonseDTO {
    private long subAreaId;
    private String subAreaName;
}
