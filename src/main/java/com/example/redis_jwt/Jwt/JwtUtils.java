package com.example.redis_jwt.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

  static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 토큰 유효성 검사
   *
   * @param secretKey
   * @param token
   * @return
   */
  public static boolean validateToken(String secretKey, String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return false;
    }
  }

  /**
   * 토큰 생성
   *
   * @param secretKey
   * @param expirationTime
   * @param dtoClass
   * @return
   */
  public static JwtResultResponse createJwtToken(String secretKey, long expirationTime, Object dtoClass) {
    try {
      byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
      SecretKey key = Keys.hmacShaKeyFor(keyBytes);

      String json = objectMapper.writeValueAsString(dtoClass);
      Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

      String token = Jwts.builder()
              .signWith(key, SignatureAlgorithm.HS256)
              .setExpiration(expirationDate)
              .claim("payload", json)
              .compact();

      return new JwtResultResponse(token, expirationDate.getTime());
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return new JwtResultResponse("", 0);
    }
  }

  /**
   * Token 복호화
   *
   * @param secretKey
   * @param jwtToken
   * @return
   */
  public static String decodeJwtToken(String secretKey, String jwtToken) {
    try {
      Jws<Claims> jws = Jwts.parserBuilder()
              .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
              .build()
              .parseClaimsJws(jwtToken);

      Claims claims = jws.getBody();
      return (String) claims.get("payload");

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }


}


// 코틀린 버전

//import com.fasterxml.jackson.databind.ObjectMapper
//import io.jsonwebtoken.Claims
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import io.jsonwebtoken.security.Keys
//import org.springframework.stereotype.Component
//import java.nio.charset.StandardCharsets
//import java.util.*
//
//
//@Component
//class JwtUtils {
//
//
//  private val objectMapper = ObjectMapper()
//
//
//  /**
//   * 토큰 유효성 검사
//   * @param token
//   * @return
//   */
//  fun validateToken(
//          secretKey: String,
//          token: String
//  ): Boolean {
//    return try {
//      val jws = Jwts.parserBuilder()
//              .setSigningKey(secretKey.toByteArray(StandardCharsets.UTF_8))
//              .build()
//              .parseClaimsJws(token)
//
//      true
//    } catch (ex: Exception) {
//      println(ex.message)
//      false
//    }
//  }
//
//
//  /**
//   * 토큰 생성
//   * @param dtoClass
//   * @return
//   */
//  fun <T> createJwtToken(
//          secretKey: String,
//          expirationTime: Long,
//          dtoClass: T
//  ): JwtResultResponse {
//    val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
//    val json = objectMapper.writeValueAsString(dtoClass)
//    return try {
//
//      val expirationTime = Date(System.currentTimeMillis() + expirationTime)
//      JwtResultResponse(
//              Jwts.builder()
//                      .signWith(key, SignatureAlgorithm.HS256)
//                      .setExpiration(expirationTime)
//                      .claim("payload", json)
//                      .compact(),
//              expirationTime.time
//      )
//
//
//    } catch (ex: Exception) {
//      print(ex.message)
//      JwtResultResponse("", 0)
//
//    }
//  }
//
//
//  /**
//   * 토큰 복호화
//   * @param jwtToken
//   * @param dtoClass
//   * @return
//   */
//  fun decodeJwtToken(
//          secretKey: String,
//          jwtToken: String
//  ): String? {
//    return try {
//      val jws = Jwts.parserBuilder()
//              .setSigningKey(secretKey.toByteArray(StandardCharsets.UTF_8))
//              .build()
//              .parseClaimsJws(jwtToken)
//
//      val claims: Claims = jws.body
//      val payloadJson = claims["payload"] as String
//
//      return payloadJson
//
//    } catch (ex: Exception) {
//      print(ex.message)
//      null
//    }
//
//  }
//
//
//}
