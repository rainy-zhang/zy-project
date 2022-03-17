package org.rainy.permission.repository;

import org.rainy.permission.entity.AclModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/29 10:20 下午
 */
@Repository
public interface AclModuleRepository extends JpaRepository<AclModule, Integer> {

    boolean existsByName(String name);

    boolean existsById(Integer id);

}
