package com.fastcampus05.zillinks.core.util.controller.email;

import com.fastcampus05.zillinks.core.util.dto.mail.MailRequest;
import com.fastcampus05.zillinks.core.util.dto.mail.MailResponse;
import com.fastcampus05.zillinks.core.util.service.mail.MailService;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
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
