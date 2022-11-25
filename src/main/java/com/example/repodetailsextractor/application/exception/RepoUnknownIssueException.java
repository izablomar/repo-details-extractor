package com.example.repodetailsextractor.application.exception;

public class RepoUnknownIssueException extends RepoPredictedException {

    public RepoUnknownIssueException() {
        super("Unknown external issue occurred while accessing repository data");
    }
}
