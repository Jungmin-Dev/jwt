package com.example.redis_jwt.response.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
  private boolean success = true;
  private String errCode = "";
  private Object errMsg = "";
  private T result;

  public ApiResponse() {
  }

  public ApiResponse(boolean success, String errCode, Object errMsg) {
    this.success = success;
    this.errCode = errCode;
    this.errMsg = errMsg;
    this.result = null;
  }

  public boolean getSuccess() {
    return success;
  }


}
