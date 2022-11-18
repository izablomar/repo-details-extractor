package com.example.repodetailsextractor.application.configuration;

import com.example.repodetailsextractor.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoDetailsAppConfig {

    @Bean
    public RepoDetailsService repoDetailsService(RepoDetailsRequestor repoDetailsRequestor, RepoDetailsCache repoDetailsCache) {
        return new RepoDetailsCacheableService(repoDetailsCache, new RepoDetailsKeyBuilder(), repoDetailsRequestor);
    }
}
