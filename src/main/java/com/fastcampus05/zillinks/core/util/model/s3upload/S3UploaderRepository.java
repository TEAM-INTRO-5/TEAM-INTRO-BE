package com.fastcampus05.zillinks.core.util.model.s3upload;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.exception.ExtConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Repository
public class S3UploaderRepository {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final String TARGET_FORMAT = "jpg";
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.delete-dir}")
    private String deleteDir;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String fileName, String type) {
        File file = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        // uploadFile JPEG 파일로 변환후 resize 진행
        if (type.equals("image")) {
            File image = transform(file);
            return upload(image, fileName);
        } else {
            return upload(file, fileName);
        }
    }

    private String upload(File uploadFile, String fileName) {
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
        // check-point
        // Default Image 일 경우 삭제하지 않는다.
        if (fileUrl.equals(DEFAULT_IMAGE))
            return;

        String fileName = fileUrl.replaceFirst(deleteDir, "");
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
            amazonS3Client.deleteObject(deleteObjectRequest);
            log.info("bucket={}", bucket);
            log.info("버킷 내 객체 삭제 성공 : {}", fileName);
        } catch (AmazonClientException e) {
            log.error("버킷 내 객체 삭제 실패: {}", e.getMessage());
            // check-point 삭제 실패시 그냥 통과시켜야하나?
            throw new Exception500("버킷 내 객체 삭제 실패");
        }
    }

    // file resize and jpg 변환
    private File transform(File file) {
        BufferedImage originalImage;
        BufferedImage resizedImage;

        String tempFileName = "temp_resized_" + file.getName();

        try {
            originalImage = ImageIO.read(new FileInputStream(file));
            resizedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();

            File tempResizedFile = new File(tempFileName);
            ImageIO.write(resizedImage, TARGET_FORMAT, tempResizedFile);

            return tempResizedFile;
        } catch (IOException e) {
            throw new Exception500("이미지 변환 실패: " + e.getMessage());
        } finally {
            removeNewFile(file);
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
