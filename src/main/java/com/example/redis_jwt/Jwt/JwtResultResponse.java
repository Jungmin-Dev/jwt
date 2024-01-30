package com.example.redis_jwt.Jwt;

import lombok.Data;

@Data
public class JwtResultResponse {
  private String token;
  private long expirationTime;

  public JwtResultResponse(String token, long expirationTime) {
    this.token = token;
    this.expirationTime = expirationTime;
  }
}

/**
 * 코틀린 버전
 * data class JwtResultResponse(
 * val token: String,
 * val expirationTime: Long
 * ) {
 * <p>
 * }
 */
