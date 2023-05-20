package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DataInit extends DummyEntity{

    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return args -> {
            userRepository.save(newUser("ssar", "쌀"));
            userRepository.save(newUser("cos", "코스"));
        };
    }
}
