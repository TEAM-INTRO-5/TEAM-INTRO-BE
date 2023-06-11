package com.fastcampus05.zillinks.core.util.service.mail;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.RandomStringGenerator;
import com.fastcampus05.zillinks.core.util.dto.mail.MailHandler;
import com.fastcampus05.zillinks.core.util.dto.mail.MailRequest;
import com.fastcampus05.zillinks.core.util.dto.mail.MailResponse;
import com.fastcampus05.zillinks.core.util.model.mail.MailValid;
import com.fastcampus05.zillinks.core.util.model.mail.MailValidRepository;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private static final String FROM_ADDRESS = "test@test.com";
    private final JavaMailSender javaMailSender;
    private final MailValidRepository mailValidRepository;
    private final UserRepository userRepository;

    @Transactional
    public MailResponse.MailOutDTO sendMail(MailRequest.MailInDTO mailInDTO) {
        if (mailInDTO.getDupCheck()) {
            if (userRepository.findByEmail(mailInDTO.getEmail()).isPresent())
                throw new Exception400("email", "이미 존재하는 이메일입니다.");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
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
        } catch (Exception e) {
            throw new Exception500("이메일 전송에 실패하였습니다.");
        }
        return MailResponse.MailOutDTO.builder()
                .code(verificationCode)
                .dupCheck(mailInDTO.getDupCheck())
                .build();
    }

    @Transactional
    public void checkMailValid(MailRequest.MailCheckInDTO mailCheckInDTO) {
        MailValid mailValidPS = mailValidRepository.findById(mailCheckInDTO.getCode()).orElseThrow(
                () -> new Exception400("code", "만료되었거나 유효하지않은 code입니다.")
        );
        mailValidRepository.delete(mailValidPS);
    }

    public String updatePassword(String email) {

        String newPassword = RandomStringGenerator.generateRandomString();

        // chaeck-point type에 따라 메일의 발송 문구가 달라진다.
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setTo(email);
            mailHandler.setSubject("비밀번호 변경 메일입니다.");
            String htmlContent = "<html>"
                    + "<head>"
                    + "<title>비밀번호 변경 메일</title>"
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
                    + "<h2>새로운 비밀번호</h2>"
                    + "<p>기존 아이디의 변경된 비밀번호를 알려드립니다.</p>"
                    + "<div class=\"verification-code\"><h2>" + newPassword + "</h2></div>"
                    + "</body>"
                    + "</html>";
            mailHandler.setText(htmlContent, true);
            mailHandler.send();
        } catch (Exception e) {
            throw new Exception500("이메일 전송에 실패하였습니다.");
        }
        return newPassword;
    }

    public void sendFile(String email, String companyName, String type, MultipartFile file) {
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setTo(email);
            mailHandler.setSubject("요청하신 " + companyName + (type.equals("intro_file") ? " 회사 소개서": " 미디어 킷") +  " 자료입니다.");
            String htmlContent = "<html>"
                    + "<head>"
                    + "<title>요청 자료</title>"
                    + "<style>"
                    + "h2 {"
                    + "color: #2c3e50;"
                    + "}"
                    + "p {"
                    + "color: #7f8c8d;"
                    + "}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<h2>" + (type.equals("intro_file") ? "회사 소개서": "미디어 킷") + " 자료 요청</h2>"
                    + "<p>요청하신 자료입니다.</p>"
                    + "</body>"
                    + "</html>";
            mailHandler.setText(htmlContent, true);
            mailHandler.setAttach(file.getOriginalFilename(), file);
//            mailHandler.setAttach(file.getName(), file);
            mailHandler.send();
        } catch (Exception e) {
            throw new Exception500("이메일 전송에 실패하였습니다.");
        }
    }
}
