package com.example.redis_jwt.Jwt;


import com.example.redis_jwt.entity.User;
import com.example.redis_jwt.response.response.ApiStatusError;
import com.example.redis_jwt.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor

public class JwtAuthInterceptor implements HandlerInterceptor {
  private final RedisService redisService;
  @Value("${jwt.secretKey}")
  private String jwtSecretKey;


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    HandlerMethod handlerMethod = (handler instanceof HandlerMethod) ? (HandlerMethod) handler : null;
    JwtAuth jwtAuth = handlerMethod.getMethodAnnotation(JwtAuth.class);

    if (jwtAuth != null) {
      String token = request.getHeader("Authorization");
      String uid = request.getHeader("uid");

      if (token == null) {
        throw new ApiStatusError(401, "토큰이 없습니다.");
      }

      // 토큰 유효성 체크
      boolean jwtBool = JwtUtils.validateToken(jwtSecretKey, token);
      // REDIS 토큰 일관성 체크
      boolean tokenExists = redisService.isTokenExists(token);

      if (jwtBool && tokenExists) {
        // 토큰 복호화 후 데이터(회원 고유 ID) 검증
        String loginData = JwtUtils.decodeJwtToken(jwtSecretKey, token);
        ObjectMapper objectMapper = new ObjectMapper();

        User loginDto = objectMapper.readValue(loginData, User.class);

        if (loginDto.getUid() != Long.parseLong(uid)) {
          return true;
        }
      } else {
        throw new ApiStatusError(401, "토큰이 유효하지 않습니다.");
      }
    }
    return true;
  }
}
