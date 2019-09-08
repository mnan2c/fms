package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.SigninConfigDto;
import com.mnan2c.fms.entity.SigninConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signinconfig")
public class SigninConfigController extends BaseCrudController<SigninConfig, SigninConfigDto> {}
