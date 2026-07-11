package com.samar.urlshortener.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
@Service
public class RateLimiterService {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${rate-limit.max-requests}") private int maxRequests;
    @Value("${rate-limit.window-seconds}") private long windowSeconds;
    public RateLimiterService(RedisTemplate<String, String> redisTemplate) { this.redisTemplate = redisTemplate; }
    public boolean isAllowed(String clientKey) {
        String redisKey = "rate-limit:" + clientKey;
        Long count = redisTemplate.opsForValue().increment(redisKey);
        if (count != null && count == 1L) redisTemplate.expire(redisKey, Duration.ofSeconds(windowSeconds));
        return count != null && count <= maxRequests;
    }
}
