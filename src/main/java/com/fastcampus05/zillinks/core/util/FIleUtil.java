package com.fastcampus05.zillinks.core.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FIleUtil {

    public static MultipartFile urlToMultipartFile(String fileUrl, String origin) throws IOException {
        // 파일 다운로드
        URL url = new URL(fileUrl);
        byte[] fileBytes;

        try (InputStream inputStream = url.openStream()) {
            fileBytes = IOUtils.toByteArray(inputStream);
        }

        // 다운로드한 파일을 MultipartFile로 변환
        DiskFileItem fileItem = new DiskFileItem("file", "application/pdf", false, origin, fileBytes.length, new File(System.getProperty("java.io.tmpdir")));
        try (OutputStream outputStream = fileItem.getOutputStream()) {
            outputStream.write(fileBytes);
        }
        return new CommonsMultipartFile(fileItem);
    }
}
