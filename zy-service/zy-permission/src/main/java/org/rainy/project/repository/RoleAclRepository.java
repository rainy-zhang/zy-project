package org.rainy.project.repository;

import org.rainy.project.entity.RoleAcl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAclRepository extends JpaRepository<RoleAcl, Integer> {

    /**
     * 根据角色Id查询对应的权限点Id列表
     *
     * @param roleId
     * @return aclIds
     */
    @Query(value = "SELECT acl_id FROM sys_role_acl WHERE role_id = :roleId", nativeQuery = true)
    List<Integer> findAclIdsByRoleId(@Param("roleId") Integer roleId);

    void deleteByRoleId(Integer roleId);

    @Query(value = "SELECT acl_id FROM sys_role_acl WHERE role_id IN (:roleIds)", nativeQuery = true)
    List<Integer> findAclIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
