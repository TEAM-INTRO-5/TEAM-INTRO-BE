package com.fastcampus05.zillinks.core.util.service.s3upload;

import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class S3UploaderService {

    private final S3UploaderRepository s3UploaderRepository;

    @Transactional
    public String upload(MultipartFile multipartFile) {
        return s3UploaderRepository.upload(multipartFile, "upload-test");
//        throw new RuntimeException("롤백 테스트");
    }

    @Transactional
    public void delete(String url) {
        log.info("url={}", url);
        s3UploaderRepository.delete(url);
    }
}
