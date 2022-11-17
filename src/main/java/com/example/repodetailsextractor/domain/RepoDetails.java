package com.example.repodetailsextractor.domain;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepoDetails {

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private Instant createdAt;

}
