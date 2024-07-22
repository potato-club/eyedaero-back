package com.gamza.eyedaero.service;



import com.gamza.eyedaero.core.error.ErrorCode;
import com.gamza.eyedaero.core.error.exeption.NotFoundException;
import com.gamza.eyedaero.core.error.exeption.UnAuthorizedException;
import com.gamza.eyedaero.dto.user.LoginRequestDto;
import com.gamza.eyedaero.dto.user.LoginResponseDto;
import com.gamza.eyedaero.dto.user.SignUpRequestDto;
import com.gamza.eyedaero.entity.UserEntity;
import com.gamza.eyedaero.entity.enums.UserRole;
import com.gamza.eyedaero.repository.UserRepository;
import com.gamza.eyedaero.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gamza.eyedaero.core.error.ErrorCode.ACCESS_DENIED_EXCEPTION;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    public void login(LoginRequestDto dto, HttpServletResponse response) {


        if(!userRepository.existsByEmail(dto.getEmail())) {
            throw new UnAuthorizedException("L401-1", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("L401-2", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        setTokenInHeader(dto.getEmail(), response);
    }


    public void regist(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .userRole(UserRole.USER)
                .build();

        userRepository.save(userEntity);
    }


    public void setTokenInHeader(String  email, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("5003", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION));

        UserRole role = user.getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), role);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), role);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }

    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        jwtTokenProvider.validateRefreshToken(refreshToken);

        String newAT = jwtTokenProvider.reissueAT(refreshToken, response);
        jwtTokenProvider.setHeaderAccessToken(response, newAT);
    }

}
