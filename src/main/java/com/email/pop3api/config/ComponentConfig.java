package com.email.pop3api.config;

import cn.hutool.core.lang.Snowflake;
import com.email.pop3api.interceptor.MDCInterceptor;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 公共注册Bean配置类
 *
 * @author serical 2019-03-24 01:49:42
 */
@Configuration
public class ComponentConfig {

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(0, 0);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    Hashids hashids() {
        return new Hashids(snowflake().nextIdStr());
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(MDCInterceptor mdcInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowCredentials(true);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(mdcInterceptor);
            }
        };
    }
}
