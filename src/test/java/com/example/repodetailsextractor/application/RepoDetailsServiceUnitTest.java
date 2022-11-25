package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.application.exception.TestPredictedRepoException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepoDetailsServiceUnitTest {

    @Test
    public void shouldFindRepoDetails() {
        //given
        RepoDetails repoDetails = RepoDetails.builder()
                .fullName("testRepoName")
                .description("testRepoDescription")
                .cloneUrl("testUrl.com")
                .stars(0)
                .createdAt("2011-01-27T19:30:43Z")
                .build();

        Mono<RepoDetails> foundRepoDetails = Mono.just(repoDetails);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        when(repoDetailsRequestorMock.request("ownerName", "repositoryName")).thenReturn(foundRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsService(repoDetailsRequestorMock).findRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectNext(repoDetails)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowExceptionWhenFindRepoDetailsFails() {
        //given
        Mono<RepoDetails> unpredictedErrorRepoDetails = Mono.error(TestPredictedRepoException::new);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        when(repoDetailsRequestorMock.request("ownerName", "repositoryName")).thenReturn(unpredictedErrorRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsService(repoDetailsRequestorMock).findRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(TestPredictedRepoException.class)
                .verify();
    }

}