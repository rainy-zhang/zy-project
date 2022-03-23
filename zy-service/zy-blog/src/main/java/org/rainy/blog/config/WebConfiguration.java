package org.rainy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: zhangyu
 * @description: Web相关配置
 * @date: in 2022/3/23 8:49 PM
 */
@Configuration
public class WebConfiguration {

    /**
     * 处理跨域问题
     *
     * @return {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        .maxAge(3600)
                        .allowedHeaders("*");
            }
        };
    }

}
