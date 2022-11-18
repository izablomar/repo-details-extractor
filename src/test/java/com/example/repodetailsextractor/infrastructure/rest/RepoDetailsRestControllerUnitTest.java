package com.example.repodetailsextractor.infrastructure.rest;

import com.example.repodetailsextractor.application.RepoDetailsService;
import com.example.repodetailsextractor.application.exception.ExceptionalRepoDetails;
import com.example.repodetailsextractor.application.exception.TestPredictedRepoException;
import com.example.repodetailsextractor.application.exception.TestUnpredictedRepoException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepoDetailsRestControllerUnitTest {

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
        RepoDetailsService repoDetailsServiceMock = mock(RepoDetailsService.class);
        when(repoDetailsServiceMock.findRepoDetails("ownerName", "repositoryName")).thenReturn(foundRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsRestController(repoDetailsServiceMock).getRepoDetails("ownerName", "repositoryName");
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
        RepoDetailsService repoDetailsServiceMock = mock(RepoDetailsService.class);
        when(repoDetailsServiceMock.findRepoDetails("ownerName", "repositoryName")).thenReturn(predictedErrorRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsRestController(repoDetailsServiceMock).getRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectNext(new ExceptionalRepoDetails("Test predicted exception's message"))
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowExceptionWhenFindRepoDetailsFails() {
        //given
        Mono<RepoDetails> unpredictedErrorRepoDetails = Mono.error(TestUnpredictedRepoException::new);
        RepoDetailsService repoDetailsServiceMock = mock(RepoDetailsService.class);
        when(repoDetailsServiceMock.findRepoDetails("ownerName", "repositoryName")).thenReturn(unpredictedErrorRepoDetails);
        //when
        Mono<RepoDetails> resultRepoDetails = new RepoDetailsRestController(repoDetailsServiceMock).getRepoDetails("ownerName", "repositoryName");
        //then
        StepVerifier.create(resultRepoDetails)
                .expectError(TestUnpredictedRepoException.class)
                .verify();
    }
}
