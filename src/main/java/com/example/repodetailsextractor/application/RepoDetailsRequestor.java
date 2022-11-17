package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.domain.RepoDetails;
import reactor.core.publisher.Mono;

public interface RepoDetailsRequestor {

    Mono<RepoDetails> request(final String owner, final String repoName);
}
