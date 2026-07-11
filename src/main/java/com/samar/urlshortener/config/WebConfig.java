package com.samar.urlshortener.config;

import com.samar.urlshortener.service.RateLimiterService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimiterService rateLimiterService;

    public WebConfig(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimiterService))
                .addPathPatterns("/api/**", "/{shortCode:[A-Za-z0-9]{7}}");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Explicitly map static resources so short codes don't get caught
        registry.addResourceHandler("/index.html")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/static/");
    }
}
