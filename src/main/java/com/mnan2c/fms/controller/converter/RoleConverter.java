package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.RoleDto;
import com.mnan2c.fms.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends BaseConverter<Role, RoleDto> {}
