package com.example.repodetailsextractor.infrastructure.http.githubapi;

import com.example.repodetailsextractor.application.exception.RepoNotFoundException;
import com.example.repodetailsextractor.application.exception.RepoUnknownIssueException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubApiResponseActionResolverUnitTest {

    @Test
    public void shouldApplyOkAction() {
        //given
        GitHubRepoDetailsResponse gitHubRepoDetailsResponse = new GitHubRepoDetailsResponse();
        gitHubRepoDetailsResponse.setFullName("testRepoName");
        gitHubRepoDetailsResponse.setDescription("testRepoDescription");
        gitHubRepoDetailsResponse.setCloneUrl("testUrl.com");
        gitHubRepoDetailsResponse.setStargazersCount(456);
        gitHubRepoDetailsResponse.setCreatedAt(Instant.ofEpochMilli(10000));

        ClientResponse clientResponseMock = mock(ClientResponse.class);
        when(clientResponseMock.statusCode()).thenReturn(HttpStatus.OK);
        when(clientResponseMock.bodyToMono(GitHubRepoDetailsResponse.class)).thenReturn(Mono.just(gitHubRepoDetailsResponse));

        RepoDetails expectedRepoDetails = RepoDetails.builder()
                .fullName("testRepoName")
                .description("testRepoDescription")
                .cloneUrl("testUrl.com")
                .stars(456)
                .createdAt(Instant.ofEpochMilli(10000))
                .build();

        GitHubRepoDetailsResponseMapper gitHubRepoDetailsResponseMapperMock = mock(GitHubRepoDetailsResponseMapper.class);
        when(gitHubRepoDetailsResponseMapperMock.apply(gitHubRepoDetailsResponse)).thenReturn(expectedRepoDetails);


        //when
        Mono<RepoDetails> resultRepoDetails = new GitHubApiResponseActionResolver().apply(clientResponseMock);

        //then
        StepVerifier.create(resultRepoDetails)
                .expectNext(expectedRepoDetails)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldApplyNotFoundAction() {
        //given
        Mono<WebClientResponseException> webClientResponseExceptionMock = Mono.just(mock(WebClientResponseException.class));
        ClientResponse clientResponseMock = mock(ClientResponse.class);
        when(clientResponseMock.statusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(clientResponseMock.createException()).thenReturn(webClientResponseExceptionMock);

        //when
        Mono<RepoDetails> resultRepoDetails = new GitHubApiResponseActionResolver().apply(clientResponseMock);

        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(RepoNotFoundException.class)
                .verify();
    }

    @Test
    public void shouldApplyUnknownIssueAction() {
        //given
        Mono<WebClientResponseException> webClientResponseExceptionMock = Mono.just(mock(WebClientResponseException.class));
        ClientResponse clientResponseMock = mock(ClientResponse.class);
        when(clientResponseMock.statusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(clientResponseMock.createException()).thenReturn(webClientResponseExceptionMock);

        //when
        Mono<RepoDetails> resultRepoDetails = new GitHubApiResponseActionResolver().apply(clientResponseMock);

        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(RepoUnknownIssueException.class)
                .verify();
    }

}