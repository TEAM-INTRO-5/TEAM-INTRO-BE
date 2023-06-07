package com.fastcampus05.zillinks.core.util.controller.email;

import com.fastcampus05.zillinks.core.util.dto.mail.MailRequest;
import com.fastcampus05.zillinks.core.util.dto.mail.MailResponse;
import com.fastcampus05.zillinks.core.util.service.mail.MailService;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

    @Operation(summary = "이메일 검증 코드 발송", description = "회원가입, 이메일 변경, 비밀번호 재설정에서 사용되는 이메일 검증 코드 발송")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = MailResponse.MailOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "mailOutDTO", description = "코드와 어떤 종류의 검증 요청인지")
    })
    @PostMapping("/validEmail")
    public ResponseEntity<?> validEmailWithoutToken(
            @RequestBody MailRequest.MailInDTO mailInDTO,
            Errors errors
    ) {
        // 발급 후 redis에 넣는다. 10분의 유효시간
        MailResponse.MailOutDTO mailOutDTO = mailService.mailSend(mailInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(mailOutDTO);
        return ResponseEntity.ok().body(responseBody);
    }

    @Operation(summary = "이메일 검증 코드 확인", description = "회원가입, 이메일 변경, 비밀번호 재설정에서 사용되는 이메일 검증 코드 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @Parameters({
            @Parameter(name = "mailOutDTO", description = "코드와 어떤 종류의 검증 요청인지")
    })
    @PostMapping("/validEmailCheck")
    public ResponseEntity<?> validEmailCheck(
            @RequestBody MailRequest.MailCheckInDTO mailCheckInDTO,
            Errors errors
    ) {
        mailService.mailValidCheck(mailCheckInDTO);
        ResponseDTO responseBody = new ResponseDTO<>(null);
        return ResponseEntity.ok().body(responseBody);
    }
}
