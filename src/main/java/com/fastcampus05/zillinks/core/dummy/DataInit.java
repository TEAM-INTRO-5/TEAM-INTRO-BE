package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
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
    CommandLineRunner init(UserRepository userRepository, IntroPageRepository introPageRepository){
        return args -> {
            User taeheoki = userRepository.save(newUser("taeheoki", "2258701327"));
            IntroPage introPage = introPageRepository.save(newIntroPage(taeheoki));
            User ssar = userRepository.save(newUser("ssar", "7058700940"));
//            userRepository.save(newUser("ssar@nate.com", 2));
        };
    }
}
