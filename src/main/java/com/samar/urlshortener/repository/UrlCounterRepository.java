package com.samar.urlshortener.repository;
import com.samar.urlshortener.model.UrlCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface UrlCounterRepository extends MongoRepository<UrlCounter, String> {}
