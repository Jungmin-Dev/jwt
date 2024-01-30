package com.example.redis_jwt.dto;

import lombok.Data;

@Data
public class LoginDto {
  private String userId;
  private String userPwd;
}
