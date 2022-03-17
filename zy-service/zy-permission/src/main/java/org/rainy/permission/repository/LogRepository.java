package org.rainy.permission.repository;

import org.rainy.permission.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/29 10:21 下午
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
