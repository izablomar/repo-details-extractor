package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.application.exception.RepoNotFoundException;
import com.example.repodetailsextractor.application.exception.RepoUnknownIssueException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class GitHubApiResponseActionResolver implements Function<ClientResponse, Mono<RepoDetails>> {

    @Override
    public Mono<RepoDetails> apply(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(GitHubRepoDetailsResponse.class).map(new GitHubRepoDetailsResponseMapper());
        } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return response.createException().flatMap(r -> Mono.error(RepoNotFoundException::new));
        } else {
            return response.createException().flatMap(r -> Mono.error(RepoUnknownIssueException::new));
        }
    }
}

