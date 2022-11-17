package com.example.repodetailsextractor.infrastructure.rest;

import com.example.repodetailsextractor.application.RepoDetailsService;
import com.example.repodetailsextractor.application.exception.ExceptionalRepoDetails;
import com.example.repodetailsextractor.domain.RepoDetails;
import com.example.repodetailsextractor.infrastructure.http.githubapi.GitHubRepoDetailsRequestor;
import com.example.repodetailsextractor.util.ResourceReader;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Instant;

class RepoDetailsRestControllerIntegrationTest {

    private static MockWebServer mockWebServer;

    private static RepoDetailsService repoDetailsService;

    private static ResourceLoader resourceLoader;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        repoDetailsService = new RepoDetailsService(new GitHubRepoDetailsRequestor(WebClient.builder(), mockWebServer.url("/")
                .toString()));
        resourceLoader = new DefaultResourceLoader();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void shouldGetRepoDetails() throws Exception {
        //given
        String mockedResponse = ResourceReader.readFileToString(resourceLoader.getResource("classpath:github-response.json"));
        mockApiResponse(HttpStatus.OK.value(), mockedResponse);

        //when
        Mono<RepoDetails> resultRepoDetails = repoDetailsService.findRepoDetails("testOwner", "testRepo");

        //then
        RepoDetails expectedRepoDetails = RepoDetails.builder()
                .fullName("octocat/Spoon-Knife")
                .description("This repo is for demonstration purposes only.")
                .cloneUrl("https://github.com/octocat/Spoon-Knife.git")
                .stars(11391)
                .createdAt(Instant.parse("2011-01-27T19:30:43Z"))
                .build();

        StepVerifier.create(resultRepoDetails)
                .expectNext(expectedRepoDetails)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldGetNotFoundExceptionalRepoDetails() {
        //given
        String mockedResponse = "[]";
        mockApiResponse(HttpStatus.NOT_FOUND.value(), mockedResponse);

        //when
        Mono<RepoDetails> resultRepoDetails = repoDetailsService.findRepoDetails("testOwner", "testRepo");

        //then
        ExceptionalRepoDetails expectedRepoDetails = new ExceptionalRepoDetails("There is no such public repository for given owner");

        StepVerifier.create(resultRepoDetails)
                .expectNext(expectedRepoDetails)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldGetRepoUnknownIssueExceptionalRepoDetails() {
        //given
        String mockedResponse = "[]";
        mockApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), mockedResponse);

        //when
        Mono<RepoDetails> resultRepoDetails = repoDetailsService.findRepoDetails("testOwner", "testRepo");

        //then
        ExceptionalRepoDetails expectedRepoDetails = new ExceptionalRepoDetails("Unknown external issue occurred while accessing repository data");

        StepVerifier.create(resultRepoDetails)
                .expectNext(expectedRepoDetails)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowUnpredictedException() {
        //given
        String mockedResponse = "defective json";
        mockApiResponse(HttpStatus.OK.value(), mockedResponse);

        //when
        Mono<RepoDetails> resultRepoDetails = repoDetailsService.findRepoDetails("testOwner", "testRepo");

        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(DecodingException.class)
                .verify();
    }

    private void mockApiResponse(int responseCode, String body) {
        MockResponse mockResponse = new MockResponse().setResponseCode(responseCode)
                .setBody(body)
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);
    }
}