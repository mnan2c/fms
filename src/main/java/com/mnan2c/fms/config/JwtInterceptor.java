package com.mnan2c.fms.config;

import com.mnan2c.fms.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 定义拦截器，验证请求携带的token信息。
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
  //  @Inject private UserRepository userRepository;

  //  @Value("${request.origin}")
  //  private String requestOrigin;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws BusinessException {
    //    String origin = request.getHeader("Origin");
    //    log.warn("request origin: [{}]", origin);
    //    response.addHeader("Access-Control-Allow-Origin", requestOrigin);
    //    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE");
    //    response.addHeader(
    //        "Access-Control-Allow-Headers",
    //        "authorization, Origin, X-Requested-With, Content-Type, Accept");
    //    response.addHeader("Access-Control-Allow-Credentials", "true");
    //    if (RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
    //      response.setStatus(HttpStatus.NO_CONTENT.value());
    //      return true;
    //    }
    //    try {
    //      String token = request.getHeader("Authorization");
    //      if (StringUtils.isBlank(token)) {
    //        throw BusinessException.instance(ErrorConsts.INVALID_TOKEN);
    //      } else {
    //        Map<String, String> tokenInfo = JwtUtils.extractToken(token);
    //        if (tokenInfo != null
    //            && !userRepository.existsByNameAndPassword(
    //                tokenInfo.get("name"), tokenInfo.get("password"))) {
    //          throw BusinessException.instance(ErrorConsts.INVALID_USERNAME_OR_PASSWORD);
    //        }
    //      }
    //    } catch (BusinessException ex) {
    //      log.warn("caught exception, code: [{}], message: [{}]", ex.getCode(), ex.getMessage());
    //      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    //      return false;
    //    }
    //    response.setStatus(HttpStatus.OK.value());
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {
    log.debug("postHandle");
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    log.debug("after completion");
  }
}
