package com.example.repodetailsextractor.infrastructure.http.githubapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitHubUrlBuilder {

    @Value("${repo-details-extractor.out.github.url}")
    private String gitHubApiUrl;

    private static final String SLASH = "/";

    public String buildUrl(final String owner, final String repoName) {
        return new StringBuilder().append(gitHubApiUrl).append(SLASH).append(owner).append(SLASH).append(repoName).toString();
    }
}
