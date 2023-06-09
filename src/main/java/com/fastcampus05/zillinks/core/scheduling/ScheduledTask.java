package com.fastcampus05.zillinks.core.scheduling;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    private final S3UploaderFileRepository s3UploaderFileRepository;
    private final AmazonS3Client amazonS3Client;

    @Scheduled(cron = "0 0 0 */3 * *") // 3 일에 한번꼴로 작업
//    @Scheduled(cron = "*/30 * * * * *") // 30 초에 한번꼴로 작업
    public void manageS3File() {
        List<S3UploaderFile> s3UploaderFiles = s3UploaderFileRepository.findAll();
        List<String> filePaths = s3UploaderFiles.stream().map(o -> o.getEncodingPath()).collect(Collectors.toList());

        try {
            ObjectListing objectListing = amazonS3Client.listObjects(bucket);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                String fileName = objectSummary.getKey();
                if (filePaths.contains(fileName) || DEFAULT_IMAGE.equals(fileName))
                    continue;
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
                amazonS3Client.deleteObject(deleteObjectRequest);
            }
            throw new Exception500("강제에러");
        } catch (AmazonServiceException e) {
            throw new Exception500(e.getMessage());

        }
    }
}
