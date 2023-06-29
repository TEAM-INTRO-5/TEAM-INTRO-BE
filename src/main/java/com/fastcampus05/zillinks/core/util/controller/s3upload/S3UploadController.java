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
import io.swagger.v3.oas.annotations.Parameters;
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
import javax.validation.constraints.Pattern;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/s/user")
public class S3UploadController {

    private final S3UploaderService s3UploaderService;

    @Operation(summary = "이미지 업로드 경로 반환", description = "이미지 저장 후 저장 경로 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = S3UploadResponse.PathResponse.class))),
    })
    @Parameters({
            @Parameter(name = "image", description = "이미지 파일"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/uploadImage")
    public ResponseEntity<S3UploadResponse.PathResponse> uploadImage(
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (!isImageFile(image)) {
            throw new Exception400("image", "image 파일이 아닙니다.");
        }
        S3UploadResponse.PathResponse uploadPath = s3UploaderService.uploadImage(image, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.CREATED, "성공", uploadPath);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }

    @Operation(summary = "파일 업로드 경로 반환", description = "파일 저장 후 저장 경로 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = S3UploadResponse.PathResponse.class))),
    })
    @Parameters({
            @Parameter(name = "file", description = "pdf 파일"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/uploadFile")
    public ResponseEntity<S3UploadResponse.PathResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (!isPdfFile(file)) {
            throw new Exception400("pdf", "pdf가 아닙니다.");
        }
        S3UploadResponse.PathResponse uploadPath = s3UploaderService.uploadFile(file, myUserDetails.getUser());
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

    private boolean isPdfFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }

        return extension.equals("pdf");
    }
}
