package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.KnowledgeDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KnowledgeDetailRepository
    extends JpaSpecificationExecutor<KnowledgeDetail>, BaseRepository<KnowledgeDetail, Integer> {}
