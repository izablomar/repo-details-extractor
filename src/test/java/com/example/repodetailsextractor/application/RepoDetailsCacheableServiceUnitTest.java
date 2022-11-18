package com.example.repodetailsextractor.application;

import com.example.repodetailsextractor.domain.RepoDetails;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class RepoDetailsCacheableServiceUnitTest {

    @Test
    public void shouldFindRepoDetailsInCache() {
        //given
        RepoDetails repoDetailsFromCache = RepoDetails.builder()
                .fullName("repo from cache")
                .build();
        RepoDetails repoDetailsFromRequest = RepoDetails.builder()
                .fullName("repo from request")
                .build();

        RepoDetailsCache repoDetailsCacheMock = mock(RepoDetailsCache.class);
        RepoDetailsKeyBuilder repoDetailsKeyBuilderMock = mock(RepoDetailsKeyBuilder.class);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        RepoDetailsCacheableService repoDetailsCacheableService = spy(new RepoDetailsCacheableService(repoDetailsCacheMock, repoDetailsKeyBuilderMock, repoDetailsRequestorMock));
        when(repoDetailsCacheMock.read("testOwner:testRepo")).thenReturn(Mono.just(repoDetailsFromCache));
        when(repoDetailsCacheMock.write("testOwner:testRepo", repoDetailsFromCache)).thenReturn(Mono.just(Boolean.TRUE));
        when(repoDetailsCacheableService.requestRepoDetails("testOwner", "testRepo")).thenReturn(Mono.just(repoDetailsFromRequest));
        when(repoDetailsKeyBuilderMock.build("testOwner", "testRepo")).thenReturn("testOwner:testRepo");

        //when
        Mono<RepoDetails> repoDetailsResult = repoDetailsCacheableService.findRepoDetails("testOwner", "testRepo");

        //then
        StepVerifier.create(repoDetailsResult)
                .expectNext(repoDetailsFromCache)
                .expectComplete()
                .verify();
    }


    @Test
    public void shouldRequestRepoDetailsIfNotInCache() {
        //given
        RepoDetails repoDetailsFromRequest = RepoDetails.builder()
                .fullName("repo from request")
                .build();

        RepoDetailsCache repoDetailsCacheMock = mock(RepoDetailsCache.class);
        RepoDetailsKeyBuilder repoDetailsKeyBuilderMock = mock(RepoDetailsKeyBuilder.class);
        RepoDetailsRequestor repoDetailsRequestorMock = mock(RepoDetailsRequestor.class);
        RepoDetailsCacheableService repoDetailsCacheableService = spy(new RepoDetailsCacheableService(repoDetailsCacheMock, repoDetailsKeyBuilderMock, repoDetailsRequestorMock));
        when(repoDetailsCacheMock.read("testOwner:testRepo")).thenReturn(Mono.empty());
        when(repoDetailsCacheMock.write("testOwner:testRepo", repoDetailsFromRequest)).thenReturn(Mono.just(Boolean.TRUE));
        when(repoDetailsCacheableService.requestRepoDetails("testOwner", "testRepo")).thenReturn(Mono.just(repoDetailsFromRequest));
        when(repoDetailsKeyBuilderMock.build("testOwner", "testRepo")).thenReturn("testOwner:testRepo");

        //when
        Mono<RepoDetails> repoDetailsResult = repoDetailsCacheableService.findRepoDetails("testOwner", "testRepo");

        //then
        StepVerifier.create(repoDetailsResult)
                .expectNext(repoDetailsFromRequest)
                .expectComplete()
                .verify();
    }

}