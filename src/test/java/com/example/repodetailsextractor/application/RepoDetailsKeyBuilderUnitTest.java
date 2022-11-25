package com.example.repodetailsextractor.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RepoDetailsKeyBuilderUnitTest {

    @Test
    public void shouldBuildRepoDetailsKey() {
        //given
        //when
        String resultkey = new RepoDetailsKeyBuilder().build("testOwner", "testRepo");

        //then
        Assertions.assertEquals("testOwner:testRepo", resultkey);
    }

}