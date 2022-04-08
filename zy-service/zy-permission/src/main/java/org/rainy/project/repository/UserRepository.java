package org.rainy.project.repository;

import org.rainy.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsById(Integer id);

    Optional<User> findByEmailOrUsername(String email, String username);

}
