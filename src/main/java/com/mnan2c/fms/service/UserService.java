package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseCrudService<User, UserDto> {}
