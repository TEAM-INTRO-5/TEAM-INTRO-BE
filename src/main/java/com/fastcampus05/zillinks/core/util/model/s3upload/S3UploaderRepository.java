package com.fastcampus05.zillinks.core.util.model.s3upload;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.exception.ExtConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploaderRepository {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName().substring(uploadFile.getName().lastIndexOf("."));
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.exists() && targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }
    }

    public void delete(String fileUrl) {
        String deleteDir = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        String fileName = fileUrl.replaceFirst(deleteDir, "");
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
            amazonS3Client.deleteObject(deleteObjectRequest);
            log.info("bucket={}", bucket);
            log.info("버킷 내 객체 삭제 성공 : {}", fileName);
        } catch (AmazonClientException e) {
            log.error("버킷 내 객체 삭제 실패: {}", e.getMessage());
            throw new Exception500("버킷 내 객체 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new ExtConvertException(e);
        }
        return Optional.empty();
    }
}
