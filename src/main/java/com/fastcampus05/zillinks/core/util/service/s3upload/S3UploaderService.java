package com.fastcampus05.zillinks.core.util.service.s3upload;

import com.fastcampus05.zillinks.core.exception.Exception400;
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
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        Optional<IntroPage> introPageOP = introPageRepository.findByUserId(userPS.getId());

//        delete(getUrlByName(introPageOP, type));

        String dir = name + "/" + type;
        String uploadPath = DEFAULT_IMAGE;
        if (image != null) {
            String fileName = makeFilePath(image, dir, "jpg");
            uploadPath = s3UploaderRepository.upload(image, fileName, "image");
        }
        S3UploaderFile s3UploaderFile = S3UploaderFile.builder()
                .originalPath(image.getOriginalFilename())
                .encodingPath(uploadPath)
                .userId(userPS.getId())
                .build();
        s3UploaderFileRepository.save(s3UploaderFile);
        return S3UploadResponse.PathResponse.builder()
                .uploadPath(uploadPath)
                .build();
    }

    @Transactional
    public S3UploadResponse.PathResponse uploadFile(MultipartFile file, String name, String type, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        Optional<IntroPage> introPageOP = introPageRepository.findByUserId(userPS.getId());

//        delete(getUrlByName(introPageOP, type));
        String dir = name + "/" + type;
        String uploadPath = null;
        if (file != null) {
            String fileName = makeFilePath(file, dir, "pdf");
            uploadPath = s3UploaderRepository.upload(file, fileName, "pdf");
        }
        S3UploaderFile s3UploaderFile = S3UploaderFile.builder()
                .originalPath(file.getOriginalFilename())
                .encodingPath(uploadPath)
                .userId(userPS.getId())
                .build();
        s3UploaderFileRepository.save(s3UploaderFile);
        return S3UploadResponse.PathResponse.builder()
                .uploadPath(uploadPath)
                .build();
    }

    private String makeFilePath(MultipartFile file, String dir, String ext) {
        String fileName = "upload/" + dir + "/" + UUID.randomUUID() + "." + ext;
        return fileName;
    }
}
