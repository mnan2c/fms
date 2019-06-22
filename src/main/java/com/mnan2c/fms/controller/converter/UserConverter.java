package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<User, UserDto> {}
