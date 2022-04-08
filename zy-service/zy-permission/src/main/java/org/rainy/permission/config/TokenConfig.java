package org.rainy.permission.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class TokenConfig {
    
    private String header;
    
    private String secret;

    /**
     * 过期时间
     */
    private int expireTime;
    
}
