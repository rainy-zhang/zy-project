package org.rainy.permission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2021/10/30 3:52 下午
 */
@EntityScan(basePackages = {"org.rainy.permission.entity"})
@SpringBootApplication
public class PermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }

}
