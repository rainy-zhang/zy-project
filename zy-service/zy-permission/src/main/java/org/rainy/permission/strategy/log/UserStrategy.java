package org.rainy.permission.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.common.exception.IllegalBeanException;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.User;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/11 13:35
 */
@Component
public class UserStrategy implements LogStrategy {

    private final UserRepository userRepository;

    public UserStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LogType type() {
        return LogType.USER;
    }

    @Override
    public void recoveryUpdate(String beforeValue, String afterValue) {
        User beforeUser = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        User afterUser = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });

        Preconditions.checkNotNull(beforeUser);
        Preconditions.checkNotNull(afterUser);

        User currentUser = userRepository.findById(beforeUser.getId()).orElseThrow(() -> new BeanNotFoundException("user not found"));

        if (!Objects.equals(currentUser, beforeUser)) {
            throw new IllegalBeanException("inconsistent with current user information，try to operate another log");
        }

        userRepository.save(beforeUser);
    }

    @Override
    public void recoveryDelete(String beforeValue) {
        User beforeUser = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(beforeUser);

        if (userRepository.existsById(beforeUser.getId())) {
            throw new IllegalParamException("user primaryKey already in use");
        }
        if (userRepository.existsByEmail(beforeUser.getEmail())) {
            throw new IllegalParamException("email already in use");
        }
        if (userRepository.existsByTelephone(beforeUser.getTelephone())) {
            throw new IllegalParamException("telephone already in use");
        }
        if (userRepository.existsByUsername(beforeUser.getUsername())) {
            throw new IllegalParamException("username already in use");
        }

        userRepository.save(beforeUser);
    }

    @Override
    public void recoveryInsert(String afterValue) {
        User afterUser = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(afterUser);

        User currentUser = userRepository.findById(afterUser.getId()).orElseThrow(() -> new BeanNotFoundException("user not found"));
        if (!Objects.equals(currentUser, afterUser)) {
            throw new IllegalBeanException("inconsistent with current user information，try to operate another log");
        }
        userRepository.deleteById(afterUser.getId());
    }

}
