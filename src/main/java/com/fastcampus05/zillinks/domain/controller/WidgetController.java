package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetRequest;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetResponse;
import com.fastcampus05.zillinks.domain.service.WidgetService;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s/user/introPage")
public class WidgetController {

    private final WidgetService widgetService;

    @Operation(summary = "제품/서비스 수정", description = "제품/서비스 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.UpdateProductsAndServicesOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteProductsAndServicesElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/productsAndServices")
    public ResponseEntity<ResponseDTO<WidgetResponse.UpdateProductsAndServicesOutDTO>> updateProductsAndServices(
            @RequestBody @Valid WidgetRequest.UpdateProductsAndServicesInDTO updateProductsAndServicesInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.UpdateProductsAndServicesOutDTO updateProductsAndServicesOutDTO = widgetService.updateProductsAndServices(updateProductsAndServicesInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateProductsAndServicesOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "제품/서비스 요소 추가", description = "제품/서비스 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveProductsAndServicesElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveProductsAndServicesElement"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/productsAndServices/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SaveProductsAndServicesElementOutDTO>> saveProductsAndServicesElement(
            @RequestBody @Valid WidgetRequest.SaveProductsAndServicesElementInDTO saveProductsAndServicesElement,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveProductsAndServicesElementOutDTO saveProductsAndServicesElementOutDTO = widgetService.saveProductsAndServicesElement(saveProductsAndServicesElement, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", saveProductsAndServicesElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "제품/서비스 요소들 삭제", description = "제품/서비스 요소들 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteProductsAndServicesElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/productsAndServices/detail")
    public ResponseEntity<ResponseDTO> deleteProductsAndServicesElements(
            @RequestBody @Valid WidgetRequest.DeleteProductsAndServicesElementsInDTO deleteProductsAndServicesElementsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        widgetService.deleteProductsAndServicesElements(deleteProductsAndServicesElementsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "팀 멤버 수정", description = "팀 멤버 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.UpdateTeamMemberOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateTeamMemberInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/teamMember")
    public ResponseEntity<ResponseDTO<WidgetResponse.UpdateTeamMemberOutDTO>> updateTeamMember(
            @RequestBody @Valid WidgetRequest.UpdateTeamMemberInDTO updateTeamMemberInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.UpdateTeamMemberOutDTO updateTeamMemberOutDTO = widgetService.updateTeamMember(updateTeamMemberInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateTeamMemberOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "팀 멤버 요소 추가", description = "팀 멤버 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveTeamMemberElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveTeamMemberElement"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/teamMember/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SaveTeamMemberElementOutDTO>> saveTeamMemberElement(
            @RequestBody @Valid WidgetRequest.SaveTeamMemberElementInDTO saveTeamMemberElement,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveTeamMemberElementOutDTO saveTeamMemberElementOutDTO = widgetService.saveTeamMemberElement(saveTeamMemberElement, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", saveTeamMemberElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "팀 멤버 요소들 삭제", description = "팀 멤버 요소들 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteProductsAndServicesElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/teamMember/detail")
    public ResponseEntity<ResponseDTO> deleteTeamMemberElements(
            @RequestBody @Valid WidgetRequest.DeleteTeamMemberElementsInDTO deleteTeamMemberElementsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        widgetService.deleteTeamMemberElements(deleteTeamMemberElementsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    /**
     * 핵심 성과
     */
    @Operation(summary = "핵심성과 수정", description = "핵심성과 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.UpdatePerformanceOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updatePerformanceInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/performance")
    public ResponseEntity<ResponseDTO<WidgetResponse.UpdatePerformanceOutDTO>> updatePerformance(
            @RequestBody @Valid WidgetRequest.UpdatePerformanceInDTO updatePerformanceInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.UpdatePerformanceOutDTO updatePerformanceOutDTO = widgetService.updatePerformance(updatePerformanceInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updatePerformanceOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "핵심성과 요소 추가", description = "핵심성과 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SavePerformanceElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "savePerformanceElementInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/performance/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SavePerformanceElementOutDTO>> savePerformanceElement(
            @RequestBody @Valid WidgetRequest.SavePerformanceElementInDTO savePerformanceElementInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SavePerformanceElementOutDTO savePerformanceElementOutDTO = widgetService.savePerformanceElement(savePerformanceElementInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", savePerformanceElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "핵심성과 요소들 삭제", description = "핵심성과 요소들 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteProductsAndServicesElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/performance/detail")
    public ResponseEntity<ResponseDTO> deletePerformanceElements(
            @RequestBody @Valid WidgetRequest.DeletePerformanceElementsInDTO deletePerformanceElementsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        widgetService.deletePerformanceElements(deletePerformanceElementsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "contact-Us", description = "map_status가 ture일 경우 full_address 값 필수")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.ContactUsOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "contactUsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/contactUs")
    public ResponseEntity<ResponseDTO> saveContactUs(
            @RequestBody @Valid WidgetRequest.ContactUsInDTO contactUsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        if (contactUsInDTO.getMapStatus() == Boolean.TRUE &&
                contactUsInDTO.getFullAddress() == null) {  // 지도 사용여부 : true 일 때, 전체 주소 필수
            throw new Exception400("full_address", "전체 주소를 입력해 주세요.");
        }
        WidgetResponse.ContactUsOutDTO contactUsOutDTO = widgetService.saveContactUs(contactUsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(contactUsOutDTO);
        return ResponseEntity.ok(responseBody);
    }

    /**
     * 팀 컬려
     */
    @Operation(summary = "팀 컬쳐 수정", description = "팀 컬쳐 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.UpdateTeamCultureOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateTeamCultureInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/teamCulture")
    public ResponseEntity<ResponseDTO<WidgetResponse.UpdateTeamCultureOutDTO>> updateTeamCulture(
            @RequestBody @Valid WidgetRequest.UpdateTeamCultureInDTO updateTeamCultureInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.UpdateTeamCultureOutDTO updateTeamCultureOutDTO = widgetService.updateTeamCulture(updateTeamCultureInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateTeamCultureOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "팀 컬려 요소 추가", description = "팀 컬려 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveTeamCultureElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveTeamCultureElementInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/teamCulture/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SavePerformanceElementOutDTO>> saveTeamCultureElement(
            @RequestBody @Valid WidgetRequest.SaveTeamCultureElementInDTO saveTeamCultureElementInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveTeamCultureElementOutDTO saveTeamCultureElementOutDTO = widgetService.saveTeamCultureElement(saveTeamCultureElementInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", saveTeamCultureElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "팀 컬려 요소들 삭제", description = "팀 컬려 요소들 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteTeamCultureElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/teamCulture/detail")
    public ResponseEntity<ResponseDTO> deleteTeamCultureElements(
            @RequestBody @Valid WidgetRequest.DeleteTeamCultureElementsInDTO deleteTeamCultureElementsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        widgetService.deleteTeamCultureElements(deleteTeamCultureElementsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "키 비주얼/슬로건", description = "키 비주얼/슬로건")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.KeyVisualAndSloganOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "keyVisualAndSloganInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/keyVisualAndSlogan")
    public ResponseEntity<ResponseDTO> saveKeyVisualAndSlogan(
            @RequestBody @Valid WidgetRequest.KeyVisualAndSloganInDTO keyVisualAndSloganInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.KeyVisualAndSloganOutDTO keyVisualAndSloganOutDTO = widgetService.saveKeyVisualAndSlogan(keyVisualAndSloganInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", keyVisualAndSloganOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "미션/비전", description = "미션/비전")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.MissionAndVisionOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "missionAndVisionInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/missionAndVision")
    public ResponseEntity<ResponseDTO> saveMissionAndVision(
            @RequestBody @Valid WidgetRequest.MissionAndVisionInDTO missionAndVisionInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.MissionAndVisionOutDTO missionAndVisionOutDTO = widgetService.saveMissionAndVision(missionAndVisionInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", missionAndVisionOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    /**
     * 연혁
     */
    @Operation(summary = "연혁 수정", description = "연혁 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.UpdateHistoryOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateHistoryInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/history")
    public ResponseEntity<ResponseDTO<WidgetResponse.UpdateHistoryOutDTO>> updateHistory(
            @RequestBody @Valid WidgetRequest.UpdateHistoryInDTO updateHistoryInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.UpdateHistoryOutDTO updateHistoryOutDTO = widgetService.updateHistory(updateHistoryInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateHistoryOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "연혁 요소 추가", description = "연혁 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveHistoryElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveHistoryElementInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/history/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SaveHistoryElementOutDTO>> saveHistoryElement(
            @RequestBody @Valid WidgetRequest.SaveHistoryElementInDTO saveHistoryElementInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveHistoryElementOutDTO saveHistoryElementOutDTO = widgetService.saveHistoryElement(saveHistoryElementInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", saveHistoryElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "연혁 요소들 삭제", description = "연혁 요소들 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "deleteHistoryElementsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @DeleteMapping("/history/detail")
    public ResponseEntity<ResponseDTO> deleteHistoryElements(
            @RequestBody @Valid WidgetRequest.DeleteHistoryElementsInDTO deleteHistoryElementsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        widgetService.deleteHistoryElements(deleteHistoryElementsInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    /**
     * 고객 리뷰
     */
    @Operation(summary = "고객 리뷰 요소 추가", description = "고객 리뷰 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveReviewElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveHistoryElementInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/review/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SaveReviewElementOutDTO>> saveReviewElement(
            @RequestBody @Valid WidgetRequest.SaveReviewElementInDTO saveReviewElementInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveReviewElementOutDTO saveReviewElementOutDTO = widgetService.saveReviewElementInDTO(saveReviewElementInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", saveReviewElementOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
