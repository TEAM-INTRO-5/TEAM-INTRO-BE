//package com.fastcampus05.zillinks.domain.controller;
//
//import com.fastcampus05.zillinks.domain.service.S3UploaderService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//public class S3UploadTestController {
//
//    private final S3UploaderService s3UploaderService;
//
//    @PostMapping("/upload-test")
//    public ResponseEntity<?> imageUpload(@RequestPart MultipartFile multipartFile) {
//
//        String url = s3UploaderService.upload(multipartFile);
//
//        return ResponseEntity.ok(url);
//    }
//
//}
