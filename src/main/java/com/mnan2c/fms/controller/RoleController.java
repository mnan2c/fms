package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.RoleDto;
import com.mnan2c.fms.entity.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseCrudController<Role, RoleDto> {}
