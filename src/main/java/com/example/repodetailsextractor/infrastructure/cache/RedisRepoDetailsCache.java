package com.example.repodetailsextractor.infrastructure.cache;

import com.example.repodetailsextractor.application.RepoDetailsCache;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RedisRepoDetailsCache implements RepoDetailsCache {
    
    private ReactiveValueOperations<String, RepoDetails> reactiveValueOps;

    public RedisRepoDetailsCache(ReactiveRedisOperations<String, RepoDetails> redisTemplate) {
        this.reactiveValueOps = redisTemplate.opsForValue();
    }

    @Override
    public Mono<Boolean> write(String key, RepoDetails repoDetails) {
        return reactiveValueOps.set(key, repoDetails);
    }

    @Override
    public Mono<RepoDetails> read(String key) {
        return reactiveValueOps.get(key);
    }
}
