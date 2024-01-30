package com.example.redis_jwt.response.response;

import lombok.Data;

@Data
public class ErrorCode extends RuntimeException {
  private final Integer errCode;
  private final String errMsg;
}
