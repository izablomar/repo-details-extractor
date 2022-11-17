package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.application.exception.ExceptionalRepoDetails;
import com.example.repodetailsextractor.application.exception.TestPredictedRepoException;
import com.example.repodetailsextractor.application.exception.TestUnpredictedRepoException;
import com.example.repodetailsextractor.domain.RepoDetails;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

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
                .createdAt(Instant.ofEpochMilli(10000))
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
    public void shouldResumePredictedExceptionWhenFindRepoDetailsFails() {
        //given
        Mono<RepoDetails> predictedErrorRepoDetails = Mono.error(TestPredictedRepoException::new);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        when(repoDetailsRequestorMock.request("ownerName", "repositoryName")).thenReturn(predictedErrorRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsService(repoDetailsRequestorMock).findRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectNext(new ExceptionalRepoDetails("Test predicted exception's message"))
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowUnpredictedExceptionWhenFindRepoDetailsFails() {
        //given
        Mono<RepoDetails> unpredictedErrorRepoDetails = Mono.error(TestUnpredictedRepoException::new);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        when(repoDetailsRequestorMock.request("ownerName", "repositoryName")).thenReturn(unpredictedErrorRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsService(repoDetailsRequestorMock).findRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(TestUnpredictedRepoException.class)
                .verify();
    }

}