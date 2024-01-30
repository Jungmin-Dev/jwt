package com.example.redis_jwt.Jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class JwtMvcConfig implements WebMvcConfigurer {
  private final JwtAuthInterceptor jwtAuthInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtAuthInterceptor);
  }
}

/**
 * 코틀린 버전
 *
 * @Configuration class JwtMvcConfig(
 * private val jwtAuthInterceptor: JwtAuthInterceptor
 * ) : WebMvcConfigurer {
 * override fun addInterceptors(registry: InterceptorRegistry) {
 * registry.addInterceptor(jwtAuthInterceptor)
 * }
 * }
 */
