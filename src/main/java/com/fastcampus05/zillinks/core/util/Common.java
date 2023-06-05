package com.fastcampus05.zillinks.core.util;

import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.dto.zillinksapi.ZillinksApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
        ResponseEntity<String> addressEntity = rt.exchange(endUri, HttpMethod.GET, httpEntity, String.class);

        ZillinksApiResponse zillinksApiResponse;
        try {
            ObjectMapper om = new ObjectMapper();
            log.info("addressEntity={}", addressEntity.getBody());
            zillinksApiResponse = om.readValue(addressEntity.getBody(), ZillinksApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new Exception500(e.getMessage());
        }
        return zillinksApiResponse;
    }

}
