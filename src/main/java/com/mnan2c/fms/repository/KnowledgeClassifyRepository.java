package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.KnowledgeClassify;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KnowledgeClassifyRepository
    extends JpaSpecificationExecutor<KnowledgeClassify>, BaseRepository<KnowledgeClassify, Integer> {}
