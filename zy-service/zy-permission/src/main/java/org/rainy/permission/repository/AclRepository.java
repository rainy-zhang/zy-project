package org.rainy.permission.repository;

import org.rainy.permission.entity.Acl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AclRepository extends JpaRepository<Acl, Integer> {

    boolean existsByNameAndAclModuleId(String name, Integer aclModuleId);

    List<Acl> findByAclModuleId(Integer aclModuleId);

    boolean existsById(Integer id);

}
