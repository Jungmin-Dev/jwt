package com.example.redis_jwt.controller;

import com.example.redis_jwt.Jwt.JwtAuth;
import com.example.redis_jwt.Jwt.JwtUtils;
import com.example.redis_jwt.dto.LoginDto;
import com.example.redis_jwt.dto.LoginTokenDto;
import com.example.redis_jwt.entity.User;
import com.example.redis_jwt.response.response.ApiError;
import com.example.redis_jwt.service.LoginService;
import com.example.redis_jwt.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class LoginController {
  private final LoginService loginService;
  private final RedisService redisService;

  @Value("${jwt.secretKey}")
  private String jwtSecretKey;

  /**
   * JWT 인증 방식 (REDIS)
   * 1. 로그인 정보 확인
   * 2. accessToken, refreshToken 발급하여 레디스에 저장한 뒤 토큰을 프론트에 반환한다. ( 프론트는 개별적으로 저장하고 관리 )
   * 3. accessToken, 회원 고유 ID를 통하여 인증을 한다.
   * 4. accessToken 만료 시 accessToken, refreshToken, 회원 고유 ID를 POST로 재발급 요청한다
   * -> 프론트에서 기간 만료 응답을 받았을 경우 자동으로 재발급 신청 (axios 인터셉터 활용)
   */

  /**
   * 회원 로그인 - 토큰 발급
   *
   * @param loginDto
   * @param response
   * @return
   */
  @GetMapping("/login")
  public ResponseEntity<HashMap<String, Object>> login(
//          @RequestBody LoginDto loginDto,
          HttpServletResponse response) {

    LoginDto loginDto = new LoginDto();
    loginDto.setUserId("test");
    loginDto.setUserPwd("test");

    HashMap<String, Object> jwt = loginService.login(loginDto);

    return ResponseEntity.ok(jwt);
  }

  /**
   * 토큰 재발급
   *
   * @param loginTokenDto
   * @return
   * @throws JsonProcessingException
   */
  @GetMapping("/reToken")
  public ResponseEntity<HashMap<String, Object>> reToken(
          @RequestBody LoginTokenDto loginTokenDto
  ) throws JsonProcessingException {
    boolean jwtBool = JwtUtils.validateToken(jwtSecretKey, loginTokenDto.getRefreshToken());

    if (jwtBool) {
      String refreshToken = redisService.getRefreshToken(loginTokenDto.getAccessToken());
      if (refreshToken.equals(loginTokenDto.getRefreshToken())) {
        // 한번 더 검증
        String loginData = JwtUtils.decodeJwtToken(jwtSecretKey, refreshToken);
        ObjectMapper objectMapper = new ObjectMapper();

        User loginDto = objectMapper.readValue(loginData, User.class);

        if (loginDto.getUid() == loginTokenDto.getUid()) {
          // ACCESS, REFRESH 재발급
          return ResponseEntity.ok(loginService.reToken(loginDto));
        }
      }
    }
    throw new ApiError("401", "다시 로그인 해주세요.");
  }

  /**
   * 검증 확인 테스트
   *
   * @return
   */
  @JwtAuth
  @GetMapping("/test")
  public String abc() {
    return "aaaa";
  }
}
