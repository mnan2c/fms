package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.controller.vo.UserVO;
import com.mnan2c.fms.entity.Role;
import com.mnan2c.fms.entity.User;
import com.mnan2c.fms.entity.UserRole;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.RoleRepository;
import com.mnan2c.fms.repository.UserRepository;
import com.mnan2c.fms.repository.UserRoleRepository;
import com.mnan2c.fms.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseCrudController<User, UserDto> {
  @Inject private UserRepository userRepository;
  @Inject private RoleRepository roleRepository;
  @Inject private UserRoleRepository userRoleRepository;

  @PostMapping("/login")
  public UserVO login(
      @RequestParam("name") String name, //
      @RequestParam("password") String password) {
    User user = userRepository.findByNameAndPassword(name, password);
    if (user == null) {
      throw BusinessException.instance(ErrorConsts.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = JwtUtils.getToken(user);
    String roleName = "";
    List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
    if (!CollectionUtils.isEmpty(userRoles)) {
      List<Integer> roleIds =
          userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
      roleName =
          roleRepository.findAllById(roleIds).stream()
              .map(Role::getName)
              .collect(Collectors.joining(","));
    }
    httpSession.setAttribute("userId", user.getId());
    httpSession.setMaxInactiveInterval(24 * 60);
    return new UserVO(user.getName(), user.getAvatar(), token, roleName);
  }

  @PostMapping("/register")
  public UserVO register(
      @RequestParam("name") String name, //
      @RequestParam("password") String password) {
    User userFromDB = userRepository.findByNameAndPassword(name, password);
    if (userFromDB != null) {
      throw BusinessException.instance(ErrorConsts.USER_ALREADY_EXISTED);
    }
    User user = new User();
    user.setName(name);
    user.setPassword(password);
    user = userRepository.save(user);
    if (user == null) {
      throw BusinessException.instance(ErrorConsts.FAILED_TO_REGISTER);
    }
    String token = JwtUtils.getToken(user);
    String roleName = "";
    List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
    if (!CollectionUtils.isEmpty(userRoles)) {
      List<Integer> roleIds =
          userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
      roleName =
          roleRepository.findAllById(roleIds).stream()
              .map(Role::getName)
              .collect(Collectors.joining(","));
    }
    httpSession.setAttribute("userId", user.getId());
    httpSession.setMaxInactiveInterval(24 * 60);
    return new UserVO(user.getName(), user.getAvatar(), token, roleName);
  }

  @PutMapping("/resetpassword")
  public UserVO resetPassword(
      @RequestParam("name") String name, //
      @RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword) {
    User user = userRepository.findByNameAndPassword(name, oldPassword);
    if (user == null) {
      log.warn("invalid old password for reset password");
      throw BusinessException.instance(ErrorConsts.INVALID_PASSWORD);
    }
    int count = userRepository.resetPassword(newPassword, user.getId());
    if (count > 0) {
      user.setPassword(newPassword);
    }
    String token = JwtUtils.getToken(user);
    String roleName = "";
    List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
    if (!CollectionUtils.isEmpty(userRoles)) {
      List<Integer> roleIds =
          userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
      roleName =
          roleRepository.findAllById(roleIds).stream()
              .map(Role::getName)
              .collect(Collectors.joining(","));
    }
    httpSession.setAttribute("userId", user.getId());
    httpSession.setMaxInactiveInterval(24 * 60);
    return new UserVO(user.getName(), user.getAvatar(), token, roleName);
  }
}
