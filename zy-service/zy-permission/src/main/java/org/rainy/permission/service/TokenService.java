package org.rainy.permission.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.rainy.common.util.CommonUtils;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.config.TokenConfig;
import org.rainy.permission.dto.UserDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class TokenService {

    private static final Long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;
    
    private final TokenConfig tokenConfig;
    private final StringRedisTemplate redisTemplate;

    public TokenService(TokenConfig tokenConfig, StringRedisTemplate redisTemplate) {
        this.tokenConfig = tokenConfig;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 创建token
     *
     * @param userDto {@link UserDto}
     * @return token
     */
    public String createToken(UserDto userDto) {
        Date issueDate = new Date();
        Date expireDate = DateUtils.addMinutes(issueDate, tokenConfig.getExpireTime());
        Map<String, Object> beanMap = CommonUtils.object2Map(userDto);

        String token = JWT.create()
                .withIssuedAt(issueDate)   // 发行时间
                .withExpiresAt(expireDate)  // 过期时间
                .withPayload(beanMap)
                .withAudience(String.valueOf(userDto.getId()))
                .sign(Algorithm.HMAC256(tokenConfig.getSecret()));
        userDto.setToken(token);
        userDto.setExpireAt(expireDate);
        redisTemplate.opsForValue().set(token, Objects.requireNonNull(JsonMapper.object2String(userDto)), Duration.ofMinutes(tokenConfig.getExpireTime()));
        return token;
    }

    /**
     * 刷新token过期时间
     *
     * @param token   token
     * @param userDto {@link UserDto}
     */
    public void refreshToken(String token, UserDto userDto) {
        long expireTime = userDto.getExpireAt().getTime();
        Date currentDate = new Date();
        if (expireTime - currentDate.getTime() > MILLIS_MINUTE_TEN) {
            return;
        }
        userDto.setExpireAt(DateUtils.addMinutes(currentDate, tokenConfig.getExpireTime()));
        redisTemplate.opsForValue().set(token, Objects.requireNonNull(JsonMapper.object2String(userDto)), Duration.ofMinutes(tokenConfig.getExpireTime()));
    }
    
}
