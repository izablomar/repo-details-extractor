package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.domain.RepoDetails;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor
public class RepoDetailsCacheableService extends RepoDetailsService {

    private RepoDetailsCache repoDetailsCache;

    private RepoDetailsKeyBuilder repoDetailsKeyBuilder;

    public RepoDetailsCacheableService(RepoDetailsCache repoDetailsCache, RepoDetailsKeyBuilder repoDetailsKeyBuilder, RepoDetailsRequestor repoDetailsRequestor) {
        super(repoDetailsRequestor);
        this.repoDetailsCache = repoDetailsCache;
        this.repoDetailsKeyBuilder = repoDetailsKeyBuilder;
    }

    @Override
    public Mono<RepoDetails> findRepoDetails(String owner, String repoName) {
        return repoDetailsCache.read(repoDetailsKeyBuilder.build(owner, repoName))
                .switchIfEmpty(requestRepoDetails(owner, repoName))
                .flatMap(repoDetails -> repoDetailsCache.write(repoDetailsKeyBuilder.build(owner, repoName), repoDetails)
                        .thenReturn(repoDetails));
    }

}