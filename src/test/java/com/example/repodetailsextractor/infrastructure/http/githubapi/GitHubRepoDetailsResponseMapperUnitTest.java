package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHubRepoDetailsResponseMapperUnitTest {

    @Test
    public void shouldApplyMapping() {
        //given
        GitHubRepoDetailsResponse gitHubRepoDetailsResponse = new GitHubRepoDetailsResponse();
        gitHubRepoDetailsResponse.setFullName("testRepoName");
        gitHubRepoDetailsResponse.setDescription("testRepoDescription");
        gitHubRepoDetailsResponse.setCloneUrl("testUrl.com");
        gitHubRepoDetailsResponse.setStargazersCount(456);
        gitHubRepoDetailsResponse.setCreatedAt(Instant.ofEpochMilli(10000));

        //when
        RepoDetails resultRepoDetails = new GitHubRepoDetailsResponseMapper().apply(gitHubRepoDetailsResponse);

        //then
        RepoDetails expectedRepoDetails = RepoDetails.builder()
                .fullName("testRepoName")
                .description("testRepoDescription")
                .cloneUrl("testUrl.com")
                .stars(456)
                .createdAt(Instant.ofEpochMilli(10000))
                .build();

        assertEquals(expectedRepoDetails, resultRepoDetails);
    }

}