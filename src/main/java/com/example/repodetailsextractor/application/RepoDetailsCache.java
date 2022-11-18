package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.domain.RepoDetails;
import reactor.core.publisher.Mono;

public interface RepoDetailsCache {

    Mono<Boolean> write(String key, RepoDetails repoDetails);

    Mono<RepoDetails> read(String key);
}
