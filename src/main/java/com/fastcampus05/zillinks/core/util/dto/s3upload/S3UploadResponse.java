package com.fastcampus05.zillinks.core.util.dto.s3upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class S3UploadResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PathResponse {
        @JsonProperty("upload_path")
        private String uploadPath;
    }
}