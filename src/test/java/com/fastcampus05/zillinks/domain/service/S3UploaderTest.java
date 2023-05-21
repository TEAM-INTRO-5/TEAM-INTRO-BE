package com.fastcampus05.zillinks.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class S3UploaderTest {

    @Test
    public void get_property_test() throws Exception {
        String property = System.getProperty("user.dir");
        log.info("property={}", property);
    }
}