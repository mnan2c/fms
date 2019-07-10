package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository
    extends JpaSpecificationExecutor<Role>, BaseRepository<Role, Integer> {}
