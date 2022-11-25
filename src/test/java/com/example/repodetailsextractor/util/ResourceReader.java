package com.example.repodetailsextractor.util;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceReader {

    public static String readFileToString(Resource resource) throws IOException {
        return FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8);
    }
}