//package com.fastcampus05.zillinks.core.util.service.s3upload;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.fastcampus05.zillinks.domain.model.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//@Transactional(readOnly = true)
//public class S3UploaderService {
//
//    private final AmazonS3Client amazonS3Client;
//    private final UserRepository userRepository;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
////    public String upload(int meetingId, MultipartFile multipartFile, String dirName) throws IOException {
////        File uploadFile = convert(multipartFile)
////                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
////
////        return upload(meetingId, uploadFile, dirName);
////    }
//
//    public String upload(MultipartFile image) {
//        File uploadFile = convert(image)
//                .orElseThrow(() -> new RuntimeException("image -> File로 전환이 실패했습니다."));
//        return upload(uploadFile);
//    }
//
//    // s3로 파일 업로드하기
////    private String upload(int meetingId, File uploadFile, String dirName) {
////        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
////        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
////        Meeting meeting = meetingRepository.findById(meetingId);
////        meeting.setImage(uploadImageUrl);
////        removeNewFile(uploadFile);
////
////        return "사진 저장 성공";
////    }
//
//    // s3로 파일 업로드하기
//    public String upload(File uploadFile) {
//        String dirName = "upload";
//        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
//        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//        removeNewFile(uploadFile);
//
//        return uploadImageUrl;
//    }
//
//    // 업로드하기
//    private String putS3(File uploadFile, String fileName){
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    // 이미지 지우기
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("File delete success");
//            return;
//        }
//        log.info("File delete fail");
//    }
//
//    private Optional<File> convert(MultipartFile file) {
//        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
//        try {
//            if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
//                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
//                    fos.write(file.getBytes());
//                }
//                return Optional.of(convertFile);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }
//
//}
