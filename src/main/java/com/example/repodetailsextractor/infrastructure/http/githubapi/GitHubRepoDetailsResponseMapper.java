package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.domain.RepoDetails;

import java.util.function.Function;

public class GitHubRepoDetailsResponseMapper implements Function<GitHubRepoDetailsResponse, RepoDetails> {

    @Override
    public RepoDetails apply(GitHubRepoDetailsResponse gitHubRepoDetailsResponse) {
        return RepoDetails.builder()
                .fullName(gitHubRepoDetailsResponse.getFullName())
                .description(gitHubRepoDetailsResponse.getDescription())
                .cloneUrl(gitHubRepoDetailsResponse.getCloneUrl())
                .stars(gitHubRepoDetailsResponse.getStargazersCount())
                .createdAt(gitHubRepoDetailsResponse.getCreatedAt())
                .build();
    }
}
