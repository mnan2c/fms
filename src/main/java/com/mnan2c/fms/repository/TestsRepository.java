package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Tests;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestsRepository
    extends JpaSpecificationExecutor<Tests>, BaseRepository<Tests, Integer> {}
