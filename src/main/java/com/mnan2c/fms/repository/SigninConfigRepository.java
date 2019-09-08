package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.SigninConfig;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SigninConfigRepository
    extends JpaSpecificationExecutor<SigninConfig>, BaseRepository<SigninConfig, Integer> {}
