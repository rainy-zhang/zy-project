package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.permission.entity.User;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.param.UserParam;
import org.rainy.permission.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    public UserService(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
    }

    public PageResult<User> pageResult(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery);
        Page<User> UserPage = userRepository.findAll(pageQuery.convert());
        return PageResult.of(UserPage);
    }

    public Optional<User> findByKeyword(String username) {
        return userRepository.findByEmailOrTelephone(username, username);
    }

    public User findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserParam userParam) {
        BeanValidator.validate(userParam, ValidateGroups.INSERT.class);
        if (userRepository.existsByEmail(userParam.getEmail())) {
            throw new IllegalParamException("email already in use");
        }
        if (userRepository.existsByUsername(userParam.getUsername())) {
            throw new IllegalParamException("username already in use");
        }
        String encryptPassword = PasswordUtils.encrypt(userParam.getPassword());
        User user = userParam.convert();
        user.setPassword(encryptPassword);
        user = userRepository.save(user);

        logService.save(null, user, LogType.USER, LogOpType.INSERT);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(UserParam userParam) {
        BeanValidator.validate(userParam, ValidateGroups.UPDATE.class);
        Optional<User> originUserOptional = userRepository.findById(userParam.getId());

        User originUser = originUserOptional.orElseThrow(() -> new BeanNotFoundException("user not found"));
        User user = userParam.convert();
        if (!Objects.equals(originUser.getEmail(), userParam.getEmail()) || !Objects.equals(originUser.getUsername(), userParam.getUsername())) {
            throw new IllegalParamException("email or username can not modified");
        }
        if (user.equals(originUser)) {
            throw new IllegalParamException("user equals originUser do nothing");
        }

        userRepository.save(user);
        logService.save(originUser, user, LogType.USER, LogOpType.UPDATE);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Optional<User> originUserOptional = userRepository.findById(id);
        User originUser = originUserOptional.orElseThrow(() -> new BeanNotFoundException("user not found"));
        userRepository.deleteById(id);
        logService.save(originUser, null, LogType.USER, LogOpType.DELETE);
    }



}
