package org.rainy.permission.repository;

import org.rainy.permission.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/29 10:16 下午
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    boolean existsByTelephone(String telephone);

    boolean existsByUsername(String username);

    boolean existsById(Integer id);

    Optional<User> findByEmailOrTelephone(String email, String telephone);

}
