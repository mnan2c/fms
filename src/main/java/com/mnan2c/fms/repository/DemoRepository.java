package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Demo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DemoRepository
    extends JpaSpecificationExecutor<Demo>, BaseRepository<Demo, Integer> {}
