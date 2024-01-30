package com.example.redis_jwt.response.response;

import lombok.Data;

@Data
public class ApiError extends RuntimeException {
  private final String errCode;
  private final String errMsg;
}
