package com.example.redis_jwt.service;

import com.example.redis_jwt.Jwt.JwtResultResponse;
import com.example.redis_jwt.Jwt.JwtUtils;
import com.example.redis_jwt.dto.LoginDto;
import com.example.redis_jwt.entity.User;
import com.example.redis_jwt.repository.UserRepository;
import com.example.redis_jwt.response.response.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LoginService {
  private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutes
  private static final long REFRESH_TOKEN_EXPIRATION = 3 * 24 * 60 * 60 * 1000; // 3 days
  private final UserRepository userRepository;
  private final RedisService redisService;

  @Value("${jwt.secretKey}")
  private String jwtSecretKey;


  public HashMap<String, Object> login(LoginDto loginDto) {
    User loginData = userRepository.findByUserId(loginDto.getUserId()).orElseThrow(
            () -> new ApiError("401", "입력한 회원정보는 일치하지 않습니다."));

    if (loginData.getUserPwd().equals(loginDto.getUserPwd())) {
      JwtResultResponse jwtToken = JwtUtils.createJwtToken(jwtSecretKey, ACCESS_TOKEN_EXPIRATION, loginData);
      JwtResultResponse jwtRefreshToken = JwtUtils.createJwtToken(jwtSecretKey, REFRESH_TOKEN_EXPIRATION, loginData);

      HashMap<String, Object> loginHashMap = new HashMap<>();

      loginHashMap.put("jwtToken", jwtToken.getToken());
      loginHashMap.put("jwtRefreshToken", jwtRefreshToken.getToken());

      redisService.saveTokensToRedis(jwtToken.getToken(), jwtRefreshToken.getToken());

      return loginHashMap;
    } else throw new ApiError("401", "입력한 회원정보는 일치하지 않습니다.");
  }

  public HashMap<String, Object> reToken(User user) {
    JwtResultResponse jwtToken = JwtUtils.createJwtToken(jwtSecretKey, ACCESS_TOKEN_EXPIRATION, user);
    JwtResultResponse jwtRefreshToken = JwtUtils.createJwtToken(jwtSecretKey, REFRESH_TOKEN_EXPIRATION, user);

    HashMap<String, Object> loginHashMap = new HashMap<>();

    loginHashMap.put("jwtToken", jwtToken.getToken());
    loginHashMap.put("jwtRefreshToken", jwtRefreshToken.getToken());

    redisService.saveTokensToRedis(jwtToken.getToken(), jwtRefreshToken.getToken());

    return loginHashMap;
  }
}
