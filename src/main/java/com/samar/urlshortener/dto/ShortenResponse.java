package com.samar.urlshortener.dto;
import java.time.LocalDateTime;
public class ShortenResponse {
    private String shortCode;
    private String shortUrl;
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    public ShortenResponse() {}
    public ShortenResponse(String shortCode, String shortUrl, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortCode = shortCode; this.shortUrl = shortUrl; this.originalUrl = originalUrl;
        this.createdAt = createdAt; this.expiresAt = expiresAt;
    }
    public String getShortCode() { return shortCode; } public void setShortCode(String s) { this.shortCode = s; }
    public String getShortUrl() { return shortUrl; } public void setShortUrl(String s) { this.shortUrl = s; }
    public String getOriginalUrl() { return originalUrl; } public void setOriginalUrl(String s) { this.originalUrl = s; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    public LocalDateTime getExpiresAt() { return expiresAt; } public void setExpiresAt(LocalDateTime t) { this.expiresAt = t; }
}
