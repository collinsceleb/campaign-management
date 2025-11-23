package com.collinsceleb.campaign_management.config;

import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @NonNull
    private final ProfileInterceptor profileInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(profileInterceptor).addPathPatterns(
                "/api/v1/campaigns/**",
                "/api/v1/campaign-location/**",
                "/api/v1/campaign-status/**");
    }
}
