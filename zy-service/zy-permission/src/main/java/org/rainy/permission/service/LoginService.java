package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.dto.UserDto;
import org.rainy.permission.entity.User;
import org.rainy.permission.exception.LoginException;
import org.rainy.permission.param.LoginParam;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    private final UserService userService;
    private final TokenService tokenService;

    public LoginService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * 处理用户登录
     *
     * @param loginParam {@link LoginParam}
     * @return token
     */
    public UserDto login(LoginParam loginParam) {
        BeanValidator.validate(loginParam);

        Optional<User> userOptional = userService.findByKeyword(loginParam.getUsername());
        User user = userOptional.orElseThrow(() -> new LoginException("username or password error"));

        String encryptPassword = PasswordUtils.encrypt(loginParam.getPassword());
        if (!Objects.equals(user.getPassword(), encryptPassword)) {
            throw new LoginException("username or password error");
        }
        UserDto userDto = UserDto.convert(user);
        userDto.setToken(tokenService.createToken(userDto));
        return userDto;
    }

    public void logout() {

    }


}
