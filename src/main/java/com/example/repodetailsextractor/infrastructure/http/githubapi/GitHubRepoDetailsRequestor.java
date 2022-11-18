package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.application.RepoDetailsRequestor;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class GitHubRepoDetailsRequestor implements RepoDetailsRequestor {

    private WebClient webClient;

    public GitHubRepoDetailsRequestor(WebClient.Builder builder, @Value("${repo-details-extractor.out.github.url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl)
                .build();
    }

    @Override
    public Mono<RepoDetails> request(String owner, String repoName) {
        return webClient.get()
                .uri("/{owner}/{repoName}", owner, repoName)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(new GitHubApiResponseActionResolver());
    }


}
