package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.util.service.s3upload.S3UploaderService;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.service.IntroPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IntroPageController {

    private final IntroPageService introPageService;

    @Operation(summary = "create IntroPage", description = "회사 소개 페이지 정보 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = IntroPageResponse.SaveOutDTO.class))),
    })
    @PostMapping("/user/introPage")
    public ResponseEntity<IntroPageResponse.SaveOutDTO> saveIntroPage(
            @RequestBody @Valid IntroPageRequest.SaveInDTO saveInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        IntroPageResponse.SaveOutDTO saveOutDTO = introPageService.createIntroPage(saveInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.CREATED, "성공", saveOutDTO);
//        return new ResponseEntity(responseBody, HttpStatus.CREATED);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }

    // check-point, logo 처음 생성시 default 연결, 이후 수정시 default가 아니라면 삭제 후 새롭게 연결
    // check-point, mediaKitFile, trackingCode 저장시 기존 데이터 삭제 후 연결

    @Operation(summary = "Make an inquery IntroPage", description = "회사 소개 페이지 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = IntroPageResponse.FindOutDTO.class))),
    })
    @GetMapping("/user/introPage")
    public ResponseEntity<IntroPageResponse.FindOutDTO> findIntroPage(
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        IntroPageResponse.FindOutDTO findOutDTO = introPageService.findIntroPage(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "Update IntroPage", description = "회사 소개 페이지 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = IntroPageResponse.UpdateOutDTO.class))),
    })
    @PatchMapping("/user/introPage")
    public ResponseEntity<IntroPageResponse.UpdateOutDTO> updateIntroPage(
            @RequestBody @Valid IntroPageRequest.UpdateInDTO updateInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        IntroPageResponse.UpdateOutDTO updateOutDTO = introPageService.updateIntroPage(updateInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
