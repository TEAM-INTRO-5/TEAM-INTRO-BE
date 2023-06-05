package com.fastcampus05.zillinks.core.util.service.s3upload;

import com.fastcampus05.zillinks.core.dummy.DummyEntity;
import com.fastcampus05.zillinks.core.util.dto.s3upload.S3UploadResponse;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.service.IntroPageService;
import com.fastcampus05.zillinks.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class S3UploaderServiceTest extends DummyEntity {

    // 진짜 userService 객체를 만들고 Mock로 Load된 모든 객체를 userService에 주입
    @InjectMocks
    private UserService userService;

    // 진짜 객체를 만들어서 Mockito 환경에 Load
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private IntroPageService introPageService;

    @Mock
    private IntroPageRepository introPageRepository;

    @Mock
    private S3UploaderRepository s3UploaderRepository;

    @InjectMocks
    private S3UploaderService s3UploaderService;

    // 가짜 객체를 만들어서 Mockito 환경에 Load
    @Mock
    private AuthenticationManager authenticationManager;

    // 진짜 객체를 만들어서 Mockito 환경에 Load
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void uploadImageTest() throws Exception {
//        // given
//        InputStream inputStream = new FileInputStream("upload/default.jpg");
//        byte[] imageBytes = inputStream.readAllBytes();
//
//        // 이미지 파일을 테스트 코드에서 읽은 후 MockMultipartFile 객체로 변환
//        MultipartFile image = new MockMultipartFile("image", "default.jpeg", "image/jpeg", imageBytes);
//
//        // stub
//        IntroPage introPage = newIntroPage();
//        User taeheoki = newUser("taeheoki@naver.com", "2258701327", introPage);
//
//        // userRepository.findById()가 taeheoki 객체를 반환하도록 설정
//        when(userRepository.findById(taeheoki.getId())).thenReturn(Optional.of(taeheoki));
//
//        // when
//        S3UploadResponse.PathResponse pathResponse = s3UploaderService.uploadImage(image, "zillinks", "logo", taeheoki);
//
//        // then
//        log.info("getUploadPath={}", pathResponse.getUploadPath());
    }



//    @Test
//    public void getUrlByNameTest() throws Exception {
//        // given
//        User taeheoki = newUser("taeheoki@nate.com", "2258701327");
//        IntroPage introPage = newMockIntroPage(1L, taeheoki);
//
//        // when
//        String logoPath = s3UploaderService.getUrlByName(introPage, "logo");
//
//        // then
//        Assertions.assertThat(logoPath).isEqualTo("logo_path1");
//    }
}