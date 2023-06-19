package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
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
}
