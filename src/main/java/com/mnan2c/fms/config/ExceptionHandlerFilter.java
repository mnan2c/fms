package com.mnan2c.fms.config;

import com.mnan2c.fms.entity.User;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.UserRepository;
import com.mnan2c.fms.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ExceptionHandlerFilter implements Filter {
  @Inject private UserRepository userRepository;

  @Value("${allow.origin}")
  private String allowOrigin;

  @Override
  public void init(FilterConfig filterConfig) {
    log.debug("filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String origin = httpServletRequest.getHeader("Origin");
    httpServletResponse.addHeader("Access-Control-Allow-Origin", allowOrigin);
    httpServletResponse.addHeader(
        "Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE");
    httpServletResponse.addHeader(
        "Access-Control-Allow-Headers",
        "authorization, Origin, X-Requested-With, Content-Type, Authorization, Accept, Cookie");
    httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
    boolean isSuccess = true;
    if (httpServletRequest.getRequestURI().equals("/api/user/login")
        || httpServletRequest.getRequestURI().equals("/api/user/register")
        || RequestMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    } else {
      try {
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
          throw BusinessException.instance(ErrorConsts.INVALID_TOKEN);
        } else {
          Map<String, String> tokenInfo = JwtUtils.extractToken(token);
          if (tokenInfo != null) {
            User user =
                userRepository.findByNameAndPassword(
                    tokenInfo.get("name"), tokenInfo.get("password"));
            if (user == null) {
              throw BusinessException.instance(ErrorConsts.INVALID_USERNAME_OR_PASSWORD);
            }
          }
        }
      } catch (BusinessException ex) {
        isSuccess = false;
        log.warn("caught exception, code: [{}], message: [{}]", ex.getCode(), ex.getMessage());
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }
    if (!isSuccess) {
      return;
    }
    if (!RequestMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    log.debug("filter destroy");
  }
}
