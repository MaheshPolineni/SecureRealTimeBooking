package com.example.leisuires.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.leisuires.components.AdminSessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminSessionInterceptor adminInterceptor;

    @Autowired
    public WebConfig(AdminSessionInterceptor adminInterceptor) {
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**") // Protect all /admin/** endpoints
                .excludePathPatterns("/admin/login");
                // .excludePathPatterns("/admin/setup");
    }
}
