package com.mnan2c.fms.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

  //  public static void setTokenCookie(String value, HttpServletResponse response) {
  //    addCookie(TOKEN_COOKIE_NAME, value, null, TOKEN_COOKIE_MAX_AGE, TOKEN_COOKIE_PATH,
  // response);
  //  }
  //
  //  public static String getTokenCookieValue(HttpServletRequest request) {
  //    return getCookieValue(request, TOKEN_COOKIE_NAME);
  //  }
  //
  //  public static void removeTokenCookie(HttpServletResponse response) {
  //    addCookie(TOKEN_COOKIE_NAME, null, null, 0, TOKEN_COOKIE_PATH, response);
  //  }

  /**
   * 添加cookie
   *
   * @param name cookie的key
   * @param value cookie的value
   * @param domain domain
   * @param path path
   * @param maxage 最长存活时间 单位为秒
   */
  public static void addCookie(
      String name,
      String value,
      String domain,
      int maxage,
      String path,
      HttpServletResponse response) {
    Cookie cookie = new Cookie(name, value);
    if (domain != null) {
      cookie.setDomain(domain);
    }
    cookie.setMaxAge(maxage);
    cookie.setPath(path);
    response.addCookie(cookie);
  }

  public static void addCookies(String name, String value, HttpServletResponse response) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(24 * 60 * 60 * 2);
    cookie.setPath("/user");
    cookie.setDomain("www.clemon.vip");
    response.addCookie(cookie);
  }

  /**
   * 往根下面存一个cookie * @param name cookie的key
   *
   * @param value cookie的value
   * @param domain domain
   * @param maxage 最长存活时间 单位为秒
   */
  public static void addCookie(
      String name, String value, String domain, int maxage, HttpServletResponse response) {
    addCookie(name, value, domain, maxage, "/", response);
  }

  /** 返回cookie值，如果没有返回 null */
  public static String getCookieValue(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        return cookie.getValue();
      }
    }
    return null;
  }

  public static void removeCookie(
      String name, String domain, HttpServletRequest request, HttpServletResponse response) {
    String cookieVal = getCookieValue(request, name);
    if (cookieVal != null) {
      CookieUtil.addCookie(name, null, domain, 0, response);
    }
  }

  //  public static void removeCookie(
  //      String name, HttpServletRequest request, HttpServletResponse response) {
  //    CookieUtil.removeCookie(name, ".dhgate.com", request, response);
  //  }
  //
  //  public static void removeCookies(
  //      String name, HttpServletRequest request, HttpServletResponse response) {
  //    String cookieVal = getCookieValue(request, name);
  //    if (cookieVal != null) {
  //      Cookie cookie = new Cookie(name, null);
  //      cookie.setMaxAge(0);
  //      response.addCookie(cookie);
  //    }
  //  }

  /**
   * save cookie
   *
   * @param user
   * @param request
   * @param response
   */
  //  public static void saveCookie(
  //      com.hvisions.mes.domain.Emp user, HttpServletRequest request, HttpServletResponse
  // response) {
  //    Security security = new Security();
  //    security.setId(user.getEmpId());
  //    security.setIp(request.getRemoteAddr());
  //    security.setTime(System.currentTimeMillis());
  //
  //    // 转JSON
  //    JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
  //    String token = jsonMapper.toJson(security);
  //
  //    // 加密
  //    token = EncryptUtil.encrypt(token);
  //
  //    // 存入Cookie
  //    CookieUtil.setTokenCookie(token, response);
  //  }
}
