package com.mnan2c.fms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

  static final String base64EncodedSecretKey = "base64EncodedSecretKey"; // 私钥
  static final long TOKEN_EXP = 1000 * 60 * 60 * 24;

  public static String getToken(String name, String password) {
    return Jwts.builder()
        .setSubject(name)
        .claim("name", name)
        .claim("password", password)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP))
        .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
        .compact();
  }

  // 解析token
  public static Map<String, String> extractToken(String token) throws ServletException {
    try {
      final Claims claims =
          Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
      Map<String, String> result = new HashMap<>();
      result.put("name", (String) claims.get("name"));
      result.put("password", (String) claims.get("password"));
      return result;
    } catch (ExpiredJwtException e1) {
      throw new ServletException("token expired");
    } catch (Exception e) {
      throw new ServletException("other token exception");
    }
  }
}
