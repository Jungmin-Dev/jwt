package com.example.redis_jwt.Jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 코틀린 버전
 *
 * @Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
 * @Retention(AnnotationRetention.RUNTIME) annotation class JwtAuth
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAuth {
}


