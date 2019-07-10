package com.mnan2c.fms.utils;

import com.mnan2c.fms.entity.User;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

  static final String base64EncodedSecretKey = "base64EncodedSecretKey"; // 私钥
  static final long TOKEN_EXP = 1000 * 60 * 60 * 24;
  //  static final long TOKEN_EXP = 1000 * 2;

  // 生成token
  public static String getToken(User user) {
    return Jwts.builder()
        .setSubject(user.getName())
        .claim("name", user.getName())
        .claim("password", user.getPassword())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP))
        .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
        .compact();
  }

  // 解析token
  public static Map<String, String> extractToken(String token) throws BusinessException {
    try {
      final Claims claims =
          Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
      Map<String, String> result = new HashMap<>();
      result.put("name", (String) claims.get("name"));
      result.put("password", (String) claims.get("password"));
      return result;
    } catch (ExpiredJwtException e1) {
      throw BusinessException.instance(ErrorConsts.TOKEN_EXPIRED);
    } catch (Exception e) {
      throw BusinessException.instance(ErrorConsts.INVALID_TOKEN);
    }
  }
}
