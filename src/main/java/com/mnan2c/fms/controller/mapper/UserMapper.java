package com.mnan2c.fms.controller.mapper;

import com.mnan2c.fms.common.AbstractMapper;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {}
