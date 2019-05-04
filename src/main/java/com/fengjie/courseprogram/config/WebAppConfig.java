package com.fengjie.courseprogram.config;

import com.fengjie.courseprogram.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fengjie
 * @date 2019:04:13
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public UserLoginInterceptor loginLnterceptor() {
        return new UserLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginLnterceptor()).addPathPatterns("/**");
    }
}
