package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;

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
        gitHubRepoDetailsResponse.setCreatedAt("2011-01-27T19:30:43Z");

        //when
        RepoDetails resultRepoDetails = new GitHubRepoDetailsResponseMapper().apply(gitHubRepoDetailsResponse);

        //then
        RepoDetails expectedRepoDetails = RepoDetails.builder()
                .fullName("testRepoName")
                .description("testRepoDescription")
                .cloneUrl("testUrl.com")
                .stars(456)
                .createdAt("2011-01-27T19:30:43Z")
                .build();

        assertEquals(expectedRepoDetails, resultRepoDetails);
    }

}