package com.example.repodetailsextractor.infrastructure.cache;

import com.example.repodetailsextractor.application.RepoDetailsCache;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RedisRepoDetailsCache implements RepoDetailsCache {

    private final long timeout;

    private ReactiveValueOperations<String, RepoDetails> reactiveValueOps;

    public RedisRepoDetailsCache(ReactiveRedisOperations<String, RepoDetails> redisTemplate, @Value("${repo-details-extractor.out.cache.timeout}") long timeout) {
        this.timeout = timeout;
        this.reactiveValueOps = redisTemplate.opsForValue();
    }

    @Override
    public Mono<Boolean> write(String key, RepoDetails repoDetails) {
        return reactiveValueOps.set(key, repoDetails, Duration.ofMinutes(timeout));
    }

    @Override
    public Mono<RepoDetails> read(String key) {
        return reactiveValueOps.get(key);
    }
}
