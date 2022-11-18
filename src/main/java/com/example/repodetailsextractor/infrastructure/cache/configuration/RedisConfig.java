package com.example.repodetailsextractor.infrastructure.cache.configuration;

import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    ReactiveRedisOperations<String, RepoDetails> redisOperations(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<RepoDetails> serializer = new Jackson2JsonRedisSerializer<>(RepoDetails.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, RepoDetails> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, RepoDetails> context = builder.value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
