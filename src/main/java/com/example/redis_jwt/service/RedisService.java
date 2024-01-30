package com.example.redis_jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
  private static final long REDIS_TOKEN_EXPIRATION = 3 * 24 * 60 * 60 * 1000; // 3 days

  private final RedisTemplate<String, String> redisTemplate;

  public void saveTokensToRedis(String accessToken, String refreshToken) {
    // Redis에 토큰 저장
    redisTemplate.opsForValue().set(accessToken, refreshToken, REDIS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
  }

  public void deleteTokensFromRedis(String accessToken) {
    // Redis에서 토큰 삭제
    redisTemplate.delete(accessToken);
  }

  // accessToken 유무 체크
  public boolean isTokenExists(String accessToken) {
    return redisTemplate.hasKey(accessToken);
  }

  // refreshToken 가져오기
  public String getRefreshToken(String key) {
    return redisTemplate.opsForValue().get(key);
  }

}
