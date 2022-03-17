package org.rainy.permission.repository;

import org.rainy.permission.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/29 10:19 下午
 */
@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Integer> {

    /**
     * 根据用户ID查询对应的角色Id列表
     * @param userId
     * @return roleIds
     */
    @Query(value = "SELECT role_id FROM sys_role_user where user_id = :userId", nativeQuery = true)
    List<Integer> findRoleIdsByUserId(@Param("userId") Integer userId);

    /**
     * 根据角色Id查询对应的用户Id列表
     * @param roleId
     * @return userIds
     */
    @Query(value = "SELECT user_id FROM sys_role_user WHERE role_id = :roleId", nativeQuery = true)
    List<Integer> findUserIdsByRoleId(@Param("roleId") Integer roleId);

    void deleteByRoleId(Integer roleId);

}
