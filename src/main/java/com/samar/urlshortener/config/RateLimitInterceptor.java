package com.samar.urlshortener.config;
import com.samar.urlshortener.exception.RateLimitExceededException;
import com.samar.urlshortener.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimiterService rateLimiterService;
    public RateLimitInterceptor(RateLimiterService rateLimiterService) { this.rateLimiterService = rateLimiterService; }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) ip = request.getRemoteAddr();
        else ip = ip.split(",")[0].trim();
        if (!rateLimiterService.isAllowed(ip)) throw new RateLimitExceededException("Rate limit exceeded. Please try again later.");
        return true;
    }
}
