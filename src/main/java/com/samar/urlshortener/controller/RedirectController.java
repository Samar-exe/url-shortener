package com.samar.urlshortener.controller;

import com.samar.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {
    
    private final UrlShortenerService urlShortenerService;
    
    public RedirectController(UrlShortenerService urlShortenerService) { 
        this.urlShortenerService = urlShortenerService; 
    }
    
    @GetMapping("/{shortCode:[a-zA-Z0-9]+}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.resolveUrl(shortCode);
        
        // Add a safety check in case the shortCode doesn't exist in your DB
        if (originalUrl == null || originalUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
