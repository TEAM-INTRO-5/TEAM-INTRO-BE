package com.fastcampus05.zillinks.core.auth.oauth;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Fetch {
    public static ResponseEntity<String> google(String url, HttpMethod method, MultiValueMap<String, String> body){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = rt.exchange(url, method, httpEntity, String.class);
        return responseEntity;
    }

    public static ResponseEntity<String> google(String url, HttpMethod method){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = rt.exchange(url, method, httpEntity, String.class);
        return responseEntity;
    }
}
