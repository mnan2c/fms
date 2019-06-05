package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.AbstractCrudController;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCrudController<User, UserDto> {}
