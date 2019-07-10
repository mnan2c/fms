package com.mnan2c.fms.repository;

import com.mnan2c.fms.controller.vo.UserVO;
import com.mnan2c.fms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository
    extends JpaSpecificationExecutor<User>, BaseRepository<User, Integer> {

  User findByNameAndPassword(String name, String password);

  User findByNameOrEmail(String name, String email);

  boolean existsByNameAndPassword(String name, String password);

  @Transactional(timeout = 10)
  @Modifying
  @Query("update User set userName = ?1 where id = ?2")
  int modifyById(String userName, Integer id);

  @Transactional(timeout = 10)
  @Modifying
  @Query("update User set password = ?1 where id = ?2")
  int resetPassword(String newPassword, Integer id);

  @Transactional
  @Modifying
  @Query("delete from User where id = ?1")
  void deleteById(Integer id);

  @Query("select u from User u")
  Page<User> findALL(Pageable pageable);

  Page<User> findByNickName(String nickName, Pageable pageable);

  Slice<User> findByNickNameAndEmail(String nickName, String email, Pageable pageable);

  @Query(
      nativeQuery = true,
      value =
          "select u.*, a.province,a.city,a.street "
              + "from user u left join address a on u.id=a.user_id where u.name like ?1")
  List<UserVO> findUserVO(String name);
}
