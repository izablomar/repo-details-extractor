package com.example.repodetailsextractor.infrastructure.rest;

import com.example.repodetailsextractor.application.RepoDetailsService;
import com.example.repodetailsextractor.application.exception.ExceptionalRepoDetails;
import com.example.repodetailsextractor.application.exception.RepoPredictedException;
import com.example.repodetailsextractor.domain.RepoDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/repositories")
public class RepoDetailsRestController {
    private RepoDetailsService repoDetailsService;

    public RepoDetailsRestController(RepoDetailsService repoDetailsService) {
        this.repoDetailsService = repoDetailsService;
    }

    @GetMapping("/{owner}/{repoName}")
    public Mono<RepoDetails> getRepoDetails(@PathVariable String owner, @PathVariable String repoName) {
        return repoDetailsService.findRepoDetails(owner, repoName)
                .onErrorResume(RepoPredictedException.class, exception -> Mono.just(new ExceptionalRepoDetails(exception.getMessage())));
    }

}
