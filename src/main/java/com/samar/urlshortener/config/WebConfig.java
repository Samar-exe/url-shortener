package com.samar.urlshortener.config;
import com.samar.urlshortener.service.RateLimiterService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RateLimiterService rateLimiterService;
    public WebConfig(RateLimiterService rateLimiterService) { this.rateLimiterService = rateLimiterService; }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimiterService)).addPathPatterns("/api/**", "/{shortCode}");
    }
}
