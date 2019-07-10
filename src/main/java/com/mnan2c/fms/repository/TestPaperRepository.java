package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.TestPaper;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestPaperRepository
    extends JpaSpecificationExecutor<TestPaper>, BaseRepository<TestPaper, Integer> {}
