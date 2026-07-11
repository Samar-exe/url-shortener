package com.samar.urlshortener.controller;
import com.samar.urlshortener.dto.ShortenRequest;
import com.samar.urlshortener.dto.ShortenResponse;
import com.samar.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    public UrlShortenerController(UrlShortenerService urlShortenerService) { this.urlShortenerService = urlShortenerService; }
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@Valid @RequestBody ShortenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlShortenerService.shortenUrl(request.getUrl()));
    }
}
