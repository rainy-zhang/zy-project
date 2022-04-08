package org.rainy.project.repository;

import org.rainy.project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);

    boolean existsById(Integer id);

}
