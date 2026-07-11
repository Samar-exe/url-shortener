package com.samar.urlshortener.repository;
import com.samar.urlshortener.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
