package org.rainy.permission.repository;

import org.rainy.permission.entity.AclModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AclModuleRepository extends JpaRepository<AclModule, Integer> {

    boolean existsByName(String name);

    boolean existsById(Integer id);

}
