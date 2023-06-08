package com.fastcampus05.zillinks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.yml")
class ZillinksApplicationTests {

    @Test
    void contextLoads() {
    }

}
