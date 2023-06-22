package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DataInit extends DummyEntity{

    @Profile({"dev", "test"})
    @Bean
    CommandLineRunner init(UserRepository userRepository, IntroPageRepository introPageRepository, S3UploaderFileRepository s3UploaderFileRepository){
        return args -> {
            User taeheoki = userRepository.save(newUser("test1", "2258701327"));
            userRepository.save(newUser("test2", "1248234535"));
            userRepository.save(newUser("test3", "7318200018"));
            userRepository.save(newUser("test4", "4668600994"));
            userRepository.save(newUser("test5", "6768700400"));
            userRepository.save(newUser("test6", "7748101583"));
            userRepository.save(newUser("test7", "4598100313"));
            userRepository.save(newUser("test8", "3108133203"));
            userRepository.save(newUser("test9", "2778701537"));
            userRepository.save(newUser("test10", "5868802285"));
        };
    }
}
