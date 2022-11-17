package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.application.exception.ExceptionalRepoDetails;
import com.example.repodetailsextractor.application.exception.RepoPredictedException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RepoDetailsService {

    private RepoDetailsRequestor repoDetailsRequestor;

    public RepoDetailsService(RepoDetailsRequestor repoDetailsRequestor) {
        this.repoDetailsRequestor = repoDetailsRequestor;
    }

    public Mono<RepoDetails> findRepoDetails(final String owner, final String repoName) {
        return repoDetailsRequestor.request(owner, repoName)
                .onErrorResume(RepoPredictedException.class, exception -> Mono.just(new ExceptionalRepoDetails(exception.getMessage())));
    }
}
