package com.samar.urlshortener.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "urls")
public class UrlMapping {
    @Id private String id;
    @Indexed(unique = true) private String shortCode;
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private long accessCount;
    public UrlMapping() {}
    public UrlMapping(String shortCode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortCode = shortCode; this.originalUrl = originalUrl;
        this.createdAt = createdAt; this.expiresAt = expiresAt; this.accessCount = 0;
    }
    public String getId() { return id; } public void setId(String id) { this.id = id; }
    public String getShortCode() { return shortCode; } public void setShortCode(String s) { this.shortCode = s; }
    public String getOriginalUrl() { return originalUrl; } public void setOriginalUrl(String s) { this.originalUrl = s; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    public LocalDateTime getExpiresAt() { return expiresAt; } public void setExpiresAt(LocalDateTime t) { this.expiresAt = t; }
    public long getAccessCount() { return accessCount; } public void setAccessCount(long c) { this.accessCount = c; }
    public void incrementAccessCount() { this.accessCount++; }
}
