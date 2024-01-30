package com.example.redis_jwt.dto;

import lombok.Data;

@Data
public class LoginTokenDto {
  private long uid;
  private String accessToken;
  private String refreshToken;
}
