package com.gamza.eyedaero.controller;

import com.gamza.eyedaero.dto.user.LoginRequestDto;
import com.gamza.eyedaero.dto.user.LoginResponseDto;
import com.gamza.eyedaero.dto.user.SignUpRequestDto;
import com.gamza.eyedaero.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        userService.login(requestDto, response);
        return ResponseEntity.ok().body("로그인 완료");

    }

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> regist(@RequestBody SignUpRequestDto requestDto, HttpServletResponse response) {
        userService.regist(requestDto);
        return ResponseEntity.ok().body("회원가입 완료");
    }

}
