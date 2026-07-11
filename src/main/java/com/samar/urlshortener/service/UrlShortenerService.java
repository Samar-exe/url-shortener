package com.samar.urlshortener.service;
import com.samar.urlshortener.dto.ShortenResponse;
import com.samar.urlshortener.exception.UrlNotFoundException;
import com.samar.urlshortener.model.UrlCounter;
import com.samar.urlshortener.model.UrlMapping;
import com.samar.urlshortener.repository.UrlCounterRepository;
import com.samar.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class UrlShortenerService {
    private static final String COUNTER_ID = "url_sequence";
    private static final String CACHE_PREFIX = "shorturl:";
    private static final Duration CACHE_TTL = Duration.ofHours(24);
    private final UrlMappingRepository urlMappingRepository;
    private final UrlCounterRepository urlCounterRepository;
    private final MongoTemplate mongoTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final Base62Encoder base62Encoder;
    @Value("${app.base-url}") private String baseUrl;
    @Value("${app.default-expiry-days}") private long defaultExpiryDays;
    public UrlShortenerService(UrlMappingRepository urlMappingRepository, UrlCounterRepository urlCounterRepository,
                                MongoTemplate mongoTemplate, RedisTemplate<String, String> redisTemplate, Base62Encoder base62Encoder) {
        this.urlMappingRepository = urlMappingRepository; this.urlCounterRepository = urlCounterRepository;
        this.mongoTemplate = mongoTemplate; this.redisTemplate = redisTemplate; this.base62Encoder = base62Encoder;
    }
    public ShortenResponse shortenUrl(String originalUrl) {
        long nextId = getNextSequenceValue();
        String shortCode = base62Encoder.encode(nextId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(defaultExpiryDays);
        urlMappingRepository.save(new UrlMapping(shortCode, originalUrl, now, expiresAt));
        redisTemplate.opsForValue().set(CACHE_PREFIX + shortCode, originalUrl, CACHE_TTL);
        return new ShortenResponse(shortCode, baseUrl + "/" + shortCode, originalUrl, now, expiresAt);
    }
    public String resolveUrl(String shortCode) {
        String cached = redisTemplate.opsForValue().get(CACHE_PREFIX + shortCode);
        if (cached != null) { incrementAccessCount(shortCode); return cached; }
        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException(shortCode));
        redisTemplate.opsForValue().set(CACHE_PREFIX + shortCode, mapping.getOriginalUrl(), CACHE_TTL);
        mapping.incrementAccessCount();
        urlMappingRepository.save(mapping);
        return mapping.getOriginalUrl();
    }
    private void incrementAccessCount(String shortCode) {
        mongoTemplate.findAndModify(new Query(where("shortCode").is(shortCode)), new Update().inc("accessCount", 1), new FindAndModifyOptions(), UrlMapping.class);
    }
    private long getNextSequenceValue() {
        Query query = new Query(where("_id").is(COUNTER_ID));
        Update update = new Update().inc("sequenceValue", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        UrlCounter counter = mongoTemplate.findAndModify(query, update, options, UrlCounter.class);
        if (counter == null) { urlCounterRepository.save(new UrlCounter(COUNTER_ID, 1L)); return 1L; }
        return counter.getSequenceValue();
    }
}
