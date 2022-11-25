package com.example.repodetailsextractor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepoDetails {

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private String createdAt;

}
