package com.example.repodetailsextractor.application;

public class RepoDetailsKeyBuilder {

    private final static String SEMICOLON = ":";

    public String build(String owner, String repoName) {
        return new StringBuilder().append(owner)
                .append(SEMICOLON)
                .append(repoName)
                .toString();
    }


}
