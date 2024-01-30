package com.example.redis_jwt.response.exception;

import com.example.redis_jwt.response.response.ApiError;
import com.example.redis_jwt.response.response.ApiResponse;
import com.example.redis_jwt.response.response.ApiStatusError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private Map<String, Object> convertToMutableMap(ApiResponse<String> apiResponse) {
    Map<String, Object> response = new java.util.HashMap<>();
    response.put("success", apiResponse.getSuccess());
    response.put("errCode", apiResponse.getErrCode());
    response.put("errMsg", apiResponse.getErrMsg());
    return response;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> accessDeniedException(AccessDeniedException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Access Denied";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "403", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Method Argument Type Mismatch";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "400", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Map<String, Object>> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Http Request Method Not Supported";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "405", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> illegalStateException(IllegalStateException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Illegal State Exception";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "500", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> runtimeException(RuntimeException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Runtime Exception";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "500", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> messageList = new ArrayList<>();
    for (org.springframework.validation.FieldError error : ex.getBindingResult().getFieldErrors()) {
      messageList.add(error.getDefaultMessage());
    }
    String message = messageList.isEmpty() ? "Method Argument Not Valid Exception" : messageList.get(0);
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "400", messageList);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Map<String, Object>> nullPointerException(NullPointerException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Null Pointer Exception";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "500", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> exception(Exception ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Exception";
    ApiResponse<String> apiResponse = new ApiResponse<>(false, "500", message);
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(ApiError.class)
  public ResponseEntity<Map<String, Object>> apiError(ApiError ex) {
    ApiResponse<String> apiResponse = new ApiResponse<>(false, ex.getErrCode(), ex.getErrMsg());
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @ExceptionHandler(ApiStatusError.class)
  public ResponseEntity<Map<String, Object>> apiStatusError(ApiStatusError ex) {
    ApiResponse<String> apiResponse = new ApiResponse<>(false, String.valueOf(ex.getStatusCode()), ex.getStatusMessage());
    Map<String, Object> response = convertToMutableMap(apiResponse);
    return ResponseEntity.status(ex.getStatusCode()).body(response);
  }
}
