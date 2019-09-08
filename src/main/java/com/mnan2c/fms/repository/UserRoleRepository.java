package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRoleRepository
    extends JpaSpecificationExecutor<UserRole>, BaseRepository<UserRole, Integer> {

  List<UserRole> findAllByUserId(Integer userId);
}
