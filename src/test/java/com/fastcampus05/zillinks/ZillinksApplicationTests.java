package com.fastcampus05.zillinks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = {"s3_region_static=us-west-1"})
@TestPropertySource(locations="classpath:application-test.yml")
class ZillinksApplicationTests {

    @Test
    void contextLoads() {
    }

}
