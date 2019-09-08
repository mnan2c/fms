package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.UserRoleDto;
import com.mnan2c.fms.entity.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userrole")
public class UserRoleController extends BaseCrudController<UserRole, UserRoleDto> {}
