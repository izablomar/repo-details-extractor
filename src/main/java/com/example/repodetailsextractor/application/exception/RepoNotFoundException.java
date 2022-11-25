package com.example.repodetailsextractor.application.exception;

public class RepoNotFoundException extends RepoPredictedException {

    public RepoNotFoundException() {
        super("There is no such public repository for given owner");
    }
}
