package com.mnan2c.fms.config;

import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.UserRepository;
import com.mnan2c.fms.utils.JwtUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// 定义拦截器，验证请求携带的token信息。
public class JwtInterceptor extends HandlerInterceptorAdapter {
  @Inject private UserRepository userRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 自动排除生成token的路径,并且如果是options请求是cors跨域预请求，设置allow对应头信息
    if (request.getRequestURI().equals("/api/user/login")
        || request.getRequestURI().equals("/api/user/register")
        || request.getRequestURI().equals("/error")
        || RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
      return true;
    }

    String token = request.getHeader("Authorization");
    if (token == null || token.trim() == "") {
      throw BusinessException.instance(ErrorConsts.INVALID_TOKEN);
    }
    try {
      Map<String, String> tokenInfo = JwtUtils.extractToken(token);
      return userRepository.existsByNameAndPassword(
          tokenInfo.get("name"), tokenInfo.get("password"));
    } catch (Exception e) {
      throw new ServletException(e.getMessage());
    }
  }
}
