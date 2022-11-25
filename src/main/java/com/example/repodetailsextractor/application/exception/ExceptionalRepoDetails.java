package com.example.repodetailsextractor.application.exception;

import com.example.repodetailsextractor.domain.RepoDetails;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class ExceptionalRepoDetails extends RepoDetails {

    private String message;
}
