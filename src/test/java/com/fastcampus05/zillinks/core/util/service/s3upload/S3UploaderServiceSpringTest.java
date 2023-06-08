//package com.fastcampus05.zillinks.core.util.service.s3upload;
//
//import com.fastcampus05.zillinks.core.dummy.DummyEntity;
//import com.fastcampus05.zillinks.core.util.dto.s3upload.S3UploadResponse;
//import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
//import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
//import com.fastcampus05.zillinks.domain.model.user.User;
//import com.fastcampus05.zillinks.domain.model.user.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//
//@Slf4j
//@ActiveProfiles("test")
//@SpringBootTest
//@TestPropertySource(locations="classpath:application-test.yml")
//@Transactional
//class S3UploaderServiceSpringTest extends DummyEntity {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private IntroPageRepository introPageRepository;
//    @Autowired
//    private S3UploaderRepository s3UploaderRepository;
//    @Autowired
//    private S3UploaderService s3UploaderService;
//
//    @Test
//    public void uploadImageTest() throws Exception {
//        // given
//        InputStream inputStream = new FileInputStream("upload/default.jpg");
//        byte[] imageBytes = inputStream.readAllBytes();
//
//        // 이미지 파일을 테스트 코드에서 읽은 후 MockMultipartFile 객체로 변환
//        MultipartFile image = new MockMultipartFile("image", "default.jpeg", "image/jpeg", imageBytes);
//
//        // stub
//        User taeheoki = userRepository.findById(1L).get();
//
//        // when
//        S3UploadResponse.PathResponse pathResponse = s3UploaderService.uploadImage(image, "zillinks", "logo", taeheoki);
//
//        // then
//        log.info("getUploadPath={}", pathResponse.getUploadPath());
//        Assertions.assertThat(pathResponse.getUploadPath().substring(pathResponse.getUploadPath().lastIndexOf("."))).isEqualTo(".jpg");
//    }
//
//
//
////    @Test
////    public void getUrlByNameTest() throws Exception {
////        // given
////        User taeheoki = newUser("taeheoki@nate.com", "2258701327");
////        IntroPage introPage = newMockIntroPage(1L, taeheoki);
////
////        // when
////        String logoPath = s3UploaderService.getUrlByName(introPage, "logo");
////
////        // then
////        Assertions.assertThat(logoPath).isEqualTo("logo_path1");
////    }
//}