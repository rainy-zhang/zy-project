package org.rainy.permission.repository;

import org.rainy.permission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/29 10:19 下午
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);

    boolean existsById(Integer id);

}
