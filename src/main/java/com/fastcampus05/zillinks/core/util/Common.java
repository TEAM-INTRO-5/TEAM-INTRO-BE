package com.fastcampus05.zillinks.core.util;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.map.KakaoAddress;
import com.fastcampus05.zillinks.core.util.dto.zillinksapi.ZillinksApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Common {

    private static final String URL = "https://api.zillinks.com/fastcampus/company";

    public static ZillinksApiResponse zillinksApi(String bizNum) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("rest-api-key", "fastcampus-team-5");
        URI endUri = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("bizNum", bizNum).build().encode().toUri();

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> addressEntity;
        try {
            addressEntity = rt.exchange(endUri, HttpMethod.GET, httpEntity, String.class);
        } catch (Exception e) {
            throw new Exception400("bizNum", "등록되지 않은 번호입니다.");
        }

        ZillinksApiResponse zillinksApiResponse;
        try {
            ObjectMapper om = new ObjectMapper();
            log.info("addressEntity={}", addressEntity.getBody());
            zillinksApiResponse = om.readValue(addressEntity.getBody(), ZillinksApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new Exception500("ZillinksApiResponse 값이 변경되었습니다.");
        }
        return zillinksApiResponse;
    }

    public static <T> ResponseEntity<byte[]> excelGenerator(List<T> data, Class<T> clazz, String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(filename);

            // create header row
            Row headerRow = sheet.createRow(0);
            List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(field.getName());
            }

            // create data rows
            for (int i = 0; i < data.size(); i++) {
                T item = data.get(i);
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);
                    Cell cell = row.createCell(j);
                    try {
                        Method getter = clazz.getMethod(getGetterName(field));
                        Object value = getter.invoke(item);
                        if (j == 0) {
                            cell.setCellValue(i + 1);
                        }
                        if (value instanceof String) {
                            cell.setCellValue((String) value);
                        } else if (value instanceof LocalDateTime) {
                            cell.setCellValue(((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yy.MM.dd")));
                        } else if (value instanceof Long) {
                            cell.setCellValue((Long) value);
                        } else if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

            byte[] bytes = null;
            HttpHeaders headers = null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);

                // Send the file in the response
                bytes = bos.toByteArray();
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", filename + ".csv"); // Add file extension .xlsx
                headers.setContentLength(bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK); // 바이트 배열과 헤더 정보를 사용하여 ResponseEntity<byte[]>를 생성하고 반환합니다.
        } catch (IOException e) {
            throw new Exception500("excel 파일 생성에 실패하였습니다.\n" + e.getMessage());
        }
    }

    private static String getGetterName(Field field) {
        String fieldName = field.getName();
        String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return "get" + capitalized;
    }

    private static final String KAKAO_REST_API_KEY = "5f0908cc84ddb23acbb2cf09654594d2";

    public static KakaoAddress kakaoSearchAddress(String url, HttpMethod method, String location) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);

        URI endUri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", location).build().encode().toUri();

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> addressEntity;

        try {
            addressEntity = rt.exchange(endUri, method, httpEntity, String.class);
        } catch (Exception e) {
            throw new Exception400("fullAddress", "주소를 찾을 수 없습니다.");
        }

        KakaoAddress kakaoAddress;
        try {
            ObjectMapper om = new ObjectMapper();
            log.info("addressEntity={}", addressEntity.getBody());
            kakaoAddress = om.readValue(addressEntity.getBody(), KakaoAddress.class);
        } catch (JsonProcessingException e) {
            throw new Exception500("지도 api 오류");
        }
        return kakaoAddress;
    }

    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        String deviceType = "Others";
        Pattern desktopPattern = Pattern.compile("Windows NT|Macintosh|Mac OS|Linux");
        Pattern mobilePattern = Pattern.compile("iPhone|Android|Mobile|Windows Phone");
        Pattern tabletPattern = Pattern.compile("iPad|iPod|Tablet|Android");

        Matcher desktopMatcher = desktopPattern.matcher(userAgent);
        Matcher mobileMatcher = mobilePattern.matcher(userAgent);
        Matcher tabletMatcher = tabletPattern.matcher(userAgent);

        if (desktopMatcher.find()) {
            deviceType = "Desktop";
        } else if (tabletMatcher.find()) {
            deviceType = "Tablet";
        } else if (mobileMatcher.find()) {
            deviceType = "Mobile";
        }

        return deviceType;
    }
}
