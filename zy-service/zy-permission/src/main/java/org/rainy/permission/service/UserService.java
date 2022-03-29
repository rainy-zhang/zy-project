package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.common.util.AvatarHelper;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.dto.UserDto;
import org.rainy.permission.entity.User;
import org.rainy.permission.param.UserParam;
import org.rainy.permission.param.VisitorParam;
import org.rainy.permission.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    public PageResult<UserDto> userPage(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery);
        Page<User> page = userRepository.findAll(pageQuery.convert());
        Page<UserDto> userPage = page.map(UserDto::convert);
        return PageResult.of(userPage);
    }

    public Optional<User> findByKeyword(String username) {
        return userRepository.findByEmailOrTelephone(username, username);
    }

    public UserDto findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new BeanNotFoundException("用户不存在"));
        return UserDto.convert(user);
    }

    public UserDto saveVisitor(VisitorParam visitorParam) throws IOException {
        BeanValidator.validate(visitorParam, ValidateGroups.INSERT.class);
        UserParam userParam = visitorParam.convert();
        return register(userParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto register(UserParam userParam) throws IOException {
        BeanValidator.validate(userParam, ValidateGroups.INSERT.class);
        if (userRepository.existsByEmail(userParam.getEmail())) {
            throw new IllegalParamException("email already in use");
        }
        if (userRepository.existsByUsername(userParam.getUsername())) {
            throw new IllegalParamException("username already in use");
        }
        String encryptPassword = PasswordUtils.encrypt(userParam.getPassword());
        User user = userParam.convert();
        user.setAvatar(AvatarHelper.createBase64Avatar(user.getUsername()));
        user.setPassword(encryptPassword);
        user = userRepository.save(user);
        logService.save(null, user, LogType.USER, LogOpType.INSERT);
        return UserDto.convert(user);
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
