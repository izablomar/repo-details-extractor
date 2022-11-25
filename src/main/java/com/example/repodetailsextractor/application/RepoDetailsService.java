package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.domain.RepoDetails;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor
public class RepoDetailsService {

    private RepoDetailsRequestor repoDetailsRequestor;

    public RepoDetailsService(RepoDetailsRequestor repoDetailsRequestor) {
        this.repoDetailsRequestor = repoDetailsRequestor;
    }

    public Mono<RepoDetails> findRepoDetails(String owner, String repoName) {
        return requestRepoDetails(owner, repoName);
    }

    protected Mono<RepoDetails> requestRepoDetails(String owner, String repoName) {
        return repoDetailsRequestor.request(owner, repoName);
    }
}
