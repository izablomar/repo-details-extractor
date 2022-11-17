package com.example.repodetailsextractor.infrastructure.http.githubapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = GitHubUrlBuilder.class)
@TestPropertySource(properties = "repo-details-extractor.out.github.url=testGitHubUrl")
class GitHubUrlBuilderUnitTest {

    @Autowired
    GitHubUrlBuilder gitHubUrlBuilder;

    @Test
    public void shouldBuildUrl() {
        //given
        //when
        String resultUrl = gitHubUrlBuilder.buildUrl("ownerName", "repositoryName");

        //then
        assertEquals("testGitHubUrl/ownerName/repositoryName", resultUrl);
    }

}