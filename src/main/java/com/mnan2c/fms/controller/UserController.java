package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.AbstractCrudController;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.entity.User;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.UserRepository;
import com.mnan2c.fms.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractCrudController<User, UserDto> {
  @Inject private UserRepository userRepository;

  @PostMapping("/login")
  public String login(
      @RequestParam("name") String name, //
      @RequestParam("password") String password) {
    boolean isUserExisted = userRepository.existsByNameAndPassword(name, password);
    if (!isUserExisted) {
      throw BusinessException.instance(ErrorConsts.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = JwtUtils.getToken(name, password);
    return token;
  }

  @PostMapping("/register")
  public String register(
      @RequestParam("name") String name, //
      @RequestParam("password") String password) {
    boolean isUserExisted = userRepository.existsByNameAndPassword(name, password);
    if (isUserExisted) {
      throw BusinessException.instance(ErrorConsts.USER_ALREADY_EXISTED);
    }
    User user = new User();
    user.setName(name);
    user.setPassword(password);
    if (userRepository.save(user) == null) {
      throw BusinessException.instance(ErrorConsts.FAILED_TO_REGISTER);
    }
    return JwtUtils.getToken(name, password);
  }
}
