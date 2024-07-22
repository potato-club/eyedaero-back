//package com.gamza.eyedaero.service;
//
//import com.gamza.eyedaero.dto.area.reponse.AreaResonseDTO;
//import com.gamza.eyedaero.entity.AreaMajorEntity;
//import com.gamza.eyedaero.entity.AreaSubEntity;
//import com.gamza.eyedaero.repository.AreaRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class AreaService {
//
//    private final AreaRepository areaRepository;
//
//    public List<AreaResonseDTO> find(Long major_id) {
//        AreaMajorEntity major = areaRepository.findById(major_id).orElse(null);
//        List<AreaSubEntity> allSub = major.getSubs();
//
//        return allSub.stream()
//                .map(areaSubEntity -> AreaResonseDTO.builder()
//                        .subAreaId(areaSubEntity.getId())
//                        .subAreaName(areaSubEntity.getName())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//}
