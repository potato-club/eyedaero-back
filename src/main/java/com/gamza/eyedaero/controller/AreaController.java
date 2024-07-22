//package com.gamza.eyedaero.controller;
//
//import com.gamza.eyedaero.dto.area.reponse.AreaResonseDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/area")
//@RequiredArgsConstructor
//public class AreaController {
//
//    private final AreaService areaService;
//
//    @GetMapping("/{major_id}")
//    public ResponseEntity<List<AreaResonseDTO>> find(@PathVariable Long major_id) {
//        List<AreaResonseDTO> allSub = areaService.find(major_id);
//        return ResponseEntity.ok(allSub);
//    }
//}
