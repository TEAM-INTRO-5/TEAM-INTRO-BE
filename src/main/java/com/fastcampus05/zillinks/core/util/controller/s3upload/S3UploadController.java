package com.fastcampus05.zillinks.core.util.controller.s3upload;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.s3upload.S3UploadResponse;
import com.fastcampus05.zillinks.core.util.service.s3upload.S3UploaderService;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class S3UploadController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/upload-test")
    public ResponseEntity<?> imageUpload(@RequestPart MultipartFile image) {
        if (!isImageFile(image)) {
            throw new Exception400("image", "image 파일이 아닙니다.");
        }

        String url = s3UploaderService.upload(image);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/delete-test")
    public ResponseEntity<?> deleteImage(@RequestParam("url") String url) {
        s3UploaderService.delete(url);
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "이미지 업로드 경로 반환", description = "이미지 저장 후 저장 경로 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = S3UploadResponse.PathResponse.class))),
    })
    @PostMapping("/upload")
    public ResponseEntity<S3UploadResponse.PathResponse> uploadImage(
            @RequestPart MultipartFile image,
            @Parameter(description = "이미지 이름", example = "example_image_name")
            @RequestParam String name,
            @Parameter(description = "이미지 유형", example = "example_image_type")
            @RequestParam String type,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (!isImageFile(image)) {
            throw new Exception400("image", "image 파일이 아닙니다.");
        }

        S3UploadResponse.PathResponse uploadPath = s3UploaderService.uploadImage(image, name, type, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.CREATED, "성공", uploadPath);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }

    private boolean isImageFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }

        return ImageIO.getImageReadersBySuffix(extension).hasNext();
    }
}
