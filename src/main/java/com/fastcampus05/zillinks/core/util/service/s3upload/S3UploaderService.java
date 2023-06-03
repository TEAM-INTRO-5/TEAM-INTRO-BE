package com.fastcampus05.zillinks.core.util.service.s3upload;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.s3upload.S3UploadResponse;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class S3UploaderService {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";
    private final S3UploaderRepository s3UploaderRepository;
    private final UserRepository userRepository;

    @Transactional
    public String upload(MultipartFile multipartFile) {
        return s3UploaderRepository.upload(multipartFile, "");
//        throw new RuntimeException("롤백 테스트");
    }

    @Transactional
    public void delete(String url) {
        log.info("url={}", url);
        s3UploaderRepository.delete(url);
    }

    @Transactional
    public S3UploadResponse.PathResponse uploadImage(MultipartFile image, String name, String type, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));

        delete(getUrlByName(userPS.getIntroPage(), type));

        String dir = "upload/" + name + "/" + type;
        String uploadPath = DEFAULT_IMAGE;
        if (image != null) {
            uploadPath = s3UploaderRepository.upload(image, dir);
        }
        return S3UploadResponse.PathResponse.builder()
                .uploadPath(uploadPath)
                .build();
    }

    private String getUrlByName(IntroPage introPage, String type) {

        Class<? extends IntroPage> introPageClass = introPage.getClass();

        Field[] fields = introPageClass.getDeclaredFields();

        try {
            for (Field field : fields) {
                if (type.equals(field.getName())) {
                    String getterName = fieldNameToGetterName(type);
                    Method getter = introPageClass.getMethod(getterName);
                    return (String) getter.invoke(introPage);
                }
            }
            throw new NoSuchMethodException("type과 일치하는 변수가 없습니다.");
        } catch (NoSuchMethodException nsme) {
            throw new Exception400("type", "잘못된 type을 입력하였습니다.");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Exception500("type의 getter 메서드 호출에 실패하였습니다." + e.getMessage());
        }
    }

    private static String fieldNameToGetterName(String fieldName) {
        String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return "get" + capitalized;
    }
}
