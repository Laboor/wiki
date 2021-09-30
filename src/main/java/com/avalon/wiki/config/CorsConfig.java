package com.avalon.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 跨域配置
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 应用于所有接口
                .allowedOriginPatterns("*")   // 允许所有跨域请求
                .allowedHeaders(CorsConfiguration.ALL)  // 允许所有请求头
                .allowedMethods(CorsConfiguration.ALL)  // 允许所有请求方法
                .allowCredentials(true)  // 允许携带凭证信息
                .maxAge(3600);  // 3600秒内不需要预请求
    }
}
