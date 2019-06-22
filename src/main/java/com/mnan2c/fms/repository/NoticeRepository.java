package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Notice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoticeRepository
    extends JpaSpecificationExecutor<Notice>, BaseRepository<Notice, Integer> {}
