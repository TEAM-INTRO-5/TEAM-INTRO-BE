package com.fastcampus05.zillinks.core.util.service.s3upload;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.s3upload.S3UploadResponse;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class S3UploaderService {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";
    private final S3UploaderRepository s3UploaderRepository;
    private final S3UploaderFileRepository s3UploaderFileRepository;
    private final UserRepository userRepository;
    private final IntroPageRepository introPageRepository;

    @Transactional
    public void delete(String url) {
        log.info("delete url={}", url);
        if (url != null)
            s3UploaderRepository.delete(url);
    }

    @Transactional
    public S3UploadResponse.PathResponse uploadImage(MultipartFile image, String name, String type, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));
        Optional<IntroPage> introPageOP = introPageRepository.findByUserId(userPS.getId());


        delete(getUrlByName(introPageOP, type));

        String dir = name + "/" + type;
        String uploadPath = DEFAULT_IMAGE;
        if (image != null) {
            String fileName = makeFilePath(image, dir, "jpg");
            uploadPath = s3UploaderRepository.upload(image, fileName, "image");
        }
        return S3UploadResponse.PathResponse.builder()
                .uploadPath(uploadPath)
                .build();
    }

    @Transactional
    public S3UploadResponse.PathResponse uploadFile(MultipartFile file, String name, String type, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));
        Optional<IntroPage> introPageOP = introPageRepository.findByUserId(userPS.getId());


        delete(getUrlByName(introPageOP, type));

        String dir = name + "/" + type;
        String uploadPath = DEFAULT_IMAGE;
        if (file != null) {
            String fileName = makeFilePath(file, dir, "pdf");
            uploadPath = s3UploaderRepository.upload(file, fileName, "pdf");
        }
        return S3UploadResponse.PathResponse.builder()
                .uploadPath(uploadPath)
                .build();
    }

    private String makeFilePath(MultipartFile file, String dir, String ext) {
        String fileName = "upload/" + dir + "/" + UUID.randomUUID() + "." + ext;
        S3UploaderFile s3UploaderFile = S3UploaderFile.builder()
                .originalPath(file.getOriginalFilename())
                .encodingPath(fileName)
                .build();
        s3UploaderFileRepository.save(s3UploaderFile);
        return fileName;
    }

    private String getUrlByName(Optional<IntroPage> introPageOP, String type) {
        if (introPageOP.isEmpty())
            return null;
        IntroPage introPagePS = introPageOP.get();
        Class<? extends IntroPage> introPageClass = introPagePS.getClass();

        Field[] fields = introPageClass.getDeclaredFields();

        try {
            for (Field field : fields) {
                if (type.equals(field.getName())) {
                    String getterName = fieldNameToGetterName(type);
                    Method getter = introPageClass.getMethod(getterName);
                    return (String) getter.invoke(introPagePS);
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
