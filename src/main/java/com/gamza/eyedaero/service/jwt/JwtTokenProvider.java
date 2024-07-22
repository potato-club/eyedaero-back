package com.gamza.eyedaero.service.jwt;

import com.gamza.eyedaero.core.error.ErrorCode;
import com.gamza.eyedaero.core.error.exeption.BadRequestException;
import com.gamza.eyedaero.core.error.exeption.ExpiredRefreshTokenException;
import com.gamza.eyedaero.core.error.exeption.NotFoundException;
import com.gamza.eyedaero.core.error.exeption.UnAuthorizedException;
import com.gamza.eyedaero.entity.UserEntity;
import com.gamza.eyedaero.entity.enums.UserRole;
import com.gamza.eyedaero.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessExpiration}")
    private long accessTokenValidTime;

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenValidTime;

    @Value("${jwt.aesKey}")
    private String aesKey;
    private SecretKeySpec getSecretKeySpec() {
        byte[] keyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
//        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
//            log.error("Invalid AES key length: {} bytes. The key must be 16, 24, or 32 bytes long.", keyBytes.length);
//            throw new IllegalArgumentException("Invalid AES key length. The key must be 16, 24, or 32 bytes long.");
//        }
        return new SecretKeySpec(keyBytes, "AES");
    }
//    private SecretKey getSigningKey() {
//        byte[] keyBytes;
//        try {
//            keyBytes = Base64.getDecoder().decode(this.secretKey);
////            keyBytes = Decoders.BASE64.decode(this.secretKey);
////            if (keyBytes.length < 32) { // 256 bits / 8 = 32 bytes
////                log.error("The provided secret key is too short. Please provide a valid Base64 encoded 256-bit key.");
////                throw new IllegalArgumentException("Invalid key length.");
////            }
//        } catch (IllegalArgumentException e) {
//            log.error("Invalid Base64 secret key: {}", e.getMessage());
//            throw new IllegalArgumentException("Invalid Base64 secret key.", e);
//        }
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Long id, long tokenValid) throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("USER_KEY", id);

        Claims claims = Jwts.claims().subject(encrypt(jsonObject.toString())).build();

        Date date = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + tokenValid))
                .signWith(getSigningKey())
                .compact();
    }

    public String createAccessToken(Long id, UserRole role) {
        try {
            return this.createToken(id, role, accessTokenValidTime, "access");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error creating access token: {}", e.getMessage());
            throw new RuntimeException("Error creating access token", e);
        }
    }

    public String createRefreshToken(Long id, UserRole role) {
        try {
            return this.createToken(id, role, refreshTokenValidTime, "refresh");
        } catch (Exception e) {
            log.error("Error creating refresh token: {}", e.getMessage());
            throw new RuntimeException("Error creating refresh token", e);
        }
    }


    public String createToken(Long id, UserRole role, long tokenValid, String tokenType) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("role", role.ordinal());
        jsonObject.addProperty("tokenType", tokenType);

        Claims claims = Jwts.claims().subject(encrypt(jsonObject.toString())).build();
        Date date = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + tokenValid))
                .signWith(getSigningKey())
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", refreshToken);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.extractUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "" , userDetails.getAuthorities());
    }

    public Long extractId(String token) {
        JsonElement id = extractValue(token).get("id");
        return id.getAsLong();
    }

    public String extractRole(String token) {
        JsonElement role = extractValue(token).get("role");
        return role.getAsString();
    }

    public String extractUserEmail(String token) {
        Long id = extractId(token);
        UserEntity userId = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당하는 사용자를 찾을 수 없습니다", ErrorCode.NOT_FOUND_EXCEPTION));
        return userId.getEmail();
    }

    public String extractTokenType(String token){
        JsonElement tokenType = extractValue(token).get("tokenType");
        return tokenType.getAsString();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(request.getHeader("Authorization") != null && extractTokenType(authorizationHeader).equals("access")) {
            return authorizationHeader;
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("refreshToken");
        if(request.getHeader("refreshToken") != null && extractTokenType(authorizationHeader).equals("refresh")) {
            return authorizationHeader;
        }
        return null;
    }
    public boolean validateRefreshToken(String refreshToken) {
        try {
            Claims claims = extractAllClaims(refreshToken);
            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
            throw new ExpiredRefreshTokenException("5002", ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
            throw new UnsupportedJwtException("JWT token is unsupported", ex);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("JWT claims string is empty", e);
        }
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Claims claims = extractAllClaims(accessToken);
            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
            throw new ExpiredJwtException(null, null, "AccessToken is Expired", e);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
            throw new UnsupportedJwtException("JWT token is unsupported", ex);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("JWT claims string is empty", e);
        } catch (Exception e) {
            log.error("Unknown error while validating access token: {}", e.getMessage());
            throw new RuntimeException("Unknown error while validating access token", e);
        }
    }

    public String reissueAT(String refreshToken, HttpServletResponse response) {
        try {
            this.validateRefreshToken(refreshToken);
            Long id = extractId(refreshToken);
            Optional<UserEntity> user = userRepository.findById(id);
            return createAccessToken(id, user.get().getUserRole());
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage();
        }
    }

    @SneakyThrows
    private String encrypt(String plainToken) {
        SecretKeySpec secretKeySpec = getSecretKeySpec();
        IvParameterSpec iv = new IvParameterSpec(aesKey.substring(0, 16).getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

        byte[] encryptionByte = cipher.doFinal(plainToken.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(encryptionByte);
    }

    @SneakyThrows
    private String decrypt(String encodeText) {
        SecretKeySpec secretKeySpec = getSecretKeySpec();
        IvParameterSpec iv = new IvParameterSpec(aesKey.substring(0, 16).getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

        byte[] decodeByte = Hex.decodeHex(encodeText);
        return new String(cipher.doFinal(decodeByte), StandardCharsets.UTF_8);
    }

    private Claims extractAllClaims(String token) {
        return getParser()
                .parseSignedClaims(token)
                .getPayload();
    }

    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build();
    }

    private JsonObject extractValue(String token)  {
        String subject = extractAllClaims(token).getSubject();
        String decrypted = decrypt(subject);
        return new Gson().fromJson(decrypted, JsonObject.class);
    }
}
