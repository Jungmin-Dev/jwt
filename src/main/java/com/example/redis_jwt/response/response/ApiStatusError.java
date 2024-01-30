package com.example.redis_jwt.response.response;

import lombok.Data;

@Data
public class ApiStatusError extends RuntimeException {
  private final Integer statusCode;
  private final String statusMessage;
}
