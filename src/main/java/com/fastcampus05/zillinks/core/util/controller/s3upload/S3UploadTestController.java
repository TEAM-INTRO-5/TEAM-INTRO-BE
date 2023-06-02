package com.fastcampus05.zillinks.core.util.controller.s3upload;

import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.core.util.service.s3upload.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class S3UploadTestController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/upload-test")
    public ResponseEntity<?> imageUpload(@RequestPart MultipartFile multipartFile) {
        String url = s3UploaderService.upload(multipartFile);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/delete-test")
    public ResponseEntity<?> deleteImage(@RequestParam("url") String url) {
        s3UploaderService.delete(url);
        return ResponseEntity.ok("ok");
    }
}
