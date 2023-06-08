package com.fastcampus05.zillinks.core.util.service.mail;

import com.fastcampus05.zillinks.core.dummy.DummyEntity;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.util.dto.mail.MailRequest;
import com.fastcampus05.zillinks.core.util.dto.mail.MailResponse;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MailServiceTest extends DummyEntity {
    @InjectMocks
    private MailService mailService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void mail_send_exception_test() throws Exception {
        // given
        MailRequest.MailInDTO mailInDTO = new MailRequest.MailInDTO("taeheoki@naver.com", "duplicateCheck");
        User taeheoki = newUser("taeheoki", "2258701327");

        // stub
        when(userRepository.findByEmail(mailInDTO.getEmail())).thenReturn(Optional.of(taeheoki));

        // when

        // then
        Assertions.assertThatThrownBy(() -> mailService.mailSend(mailInDTO))
                .isInstanceOf(Exception400.class);
    }
}