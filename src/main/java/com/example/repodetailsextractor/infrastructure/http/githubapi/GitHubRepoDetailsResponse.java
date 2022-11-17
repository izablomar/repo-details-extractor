package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitHubRepoDetailsResponse {

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stargazersCount;
    private Instant createdAt;

}
