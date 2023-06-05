package com.fastcampus05.zillinks.core.util.service.mail;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.util.dto.mail.MailHandler;
import com.fastcampus05.zillinks.core.util.dto.mail.MailRequest;
import com.fastcampus05.zillinks.core.util.dto.mail.MailResponse;
import com.fastcampus05.zillinks.core.util.model.mail.MailValid;
import com.fastcampus05.zillinks.core.util.model.mail.MailValidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private static final String FROM_ADDRESS = "test@test.com";
    private final JavaMailSender javaMailSender;
    private final MailValidRepository mailValidRepository;

    @Transactional
    public MailResponse.MailOutDTO mailSend(MailRequest.MailInDTO mailInDTO){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            sb.append((char) (random.nextInt(26) + 'A'));
        }
        String verificationCode = sb.toString();

        // chaeck-point type에 따라 메일의 발송 문구가 달라진다.
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setTo(mailInDTO.getEmail());
            mailHandler.setSubject("인증메일입니다.");
            String htmlContent = "<html>"
                    + "<head>"
                    + "<title>이메일 인증</title>"
                    + "<style>"
                    + "h2 {"
                    + "color: #2c3e50;"
                    + "}"
                    + "p {"
                    + "color: #7f8c8d;"
                    + "}"
                    + ".verification-code {"
                    + "background-color: #f5f5f5;"
                    + "color: #595959;"
                    + "border-radius: 10px;"
                    + "padding: 20px;"
                    + "margin-top: 20px;"
                    + "}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<h2>인증 코드</h2>"
                    + "<p>본인 인증을 위해 아래의 인증 코드를 입력해주세요.</p>"
                    + "<div class=\"verification-code\"><h2>" + verificationCode + "</h2></div>"
                    + "</body>"
                    + "</html>";
            mailHandler.setText(htmlContent, true);
            mailHandler.send();
            // redis에 저장하여 유효시간 10분 부여하여야 한다.
            MailValid mailValid = new MailValid(verificationCode);
            mailValidRepository.save(mailValid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return MailResponse.MailOutDTO.builder()
                .code(verificationCode)
                .type(mailInDTO.getType())
                .build();
    }

    @Transactional
    public void mailValidCheck(MailRequest.MailCheckInDTO mailCheckInDTO) {
        MailValid mailValidPS = mailValidRepository.findById(mailCheckInDTO.getCode()).orElseThrow(
                () -> new Exception400("code", "만료되었거나 유효하지않은 code입니다.")
        );
        mailValidRepository.delete(mailValidPS);
    }
}
