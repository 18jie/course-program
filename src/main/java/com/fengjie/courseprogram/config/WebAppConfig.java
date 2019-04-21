package com.fengjie.courseprogram.config;

import com.fengjie.courseprogram.interceptor.LoginInterceptor;
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
    public LoginInterceptor loginLnterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginLnterceptor()).addPathPatterns("/**");
    }
}
