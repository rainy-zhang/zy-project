package org.rainy.permission.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.dto.UserDto;
import org.rainy.permission.entity.User;
import org.rainy.permission.exception.LoginException;
import org.rainy.permission.param.LoginParam;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {
    
    private final UserService userService;
    private final StringRedisTemplate redisTemplate;

    public LoginService(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }
    
    private static final String TOKEN_KEY = "USER-TOKEN";
    private static final Long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;

    /**
     * 处理用户登录
     * @param loginParam {@link LoginParam}
     * @return token
     */
    public String login(LoginParam loginParam) {
        BeanValidator.validate(loginParam);
        
        Optional<User> userOptional = userService.findByKeyword(loginParam.getUsername());
        User user = userOptional.orElseThrow(() -> new LoginException("username or password error"));

        String encryptPassword = PasswordUtils.encrypt(loginParam.getPassword());
        if (Objects.equals(user.getPassword(), encryptPassword)) {
            throw new LoginException("username or password error");
        }
        String token = createToken(user);
        
        UserDto userDto = UserDto.convert(user);
        userDto.setToken(token);
        saveToken(userDto);
        return token;
    }
    
    /**
     * 创建token
     * @param user {@link User}
     * @return token
     */
    private String createToken(User user) {
        Date issueDate = new Date();
        Date expireDate = DateUtils.addMinutes(issueDate, 30);
        return JWT.create()
                .withIssuedAt(issueDate)   // 发行时间
                .withExpiresAt(expireDate)  // 过期时间
                .withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * 刷新token过期时间
     * @param userDto {@link UserDto}
     */
    public void refreshToken(UserDto userDto) {
        long expireTime = userDto.getExpireAt().getTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime > MILLIS_MINUTE_TEN) {
            return;
        }
        saveToken(userDto);
    }

    public void saveToken(UserDto userDto) {
        String tokenKey = generateTokenKey(userDto);
        redisTemplate.opsForValue().set(tokenKey, userDto.getToken());
    }
    
    private String generateTokenKey(UserDto userDto) {
        return String.format("%s-%s",TOKEN_KEY, userDto.getId());
    }
    
}
