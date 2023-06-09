package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.SaveStatus;
import com.fastcampus05.zillinks.domain.model.intropage.WebPageInfo;
import com.fastcampus05.zillinks.domain.model.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyEntity {
    public User newUser(String loginId, String bizNum){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .loginId(loginId)
                .email(loginId + "@naver.com")
                .password(passwordEncoder.encode("1234"))
                .bizNum(bizNum)
                .role("USER")
                .build();
    }
//
//    public User newMockUser(Long id, String username, String fullName){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return User.builder()
//                .id(id)
//                .username(username)
//                .password(passwordEncoder.encode("1234"))
//                .fullName(fullName)
//                .email(username+"@nate.com")
//                .role("USER")
//                .status(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//    }

    public IntroPage newIntroPage() {
        IntroPage introPage = IntroPage.builder()
                .webPageInfo(new WebPageInfo("pavicon_path", "webpage_name", "sub_domain", "title", "description"))
                .zillinksData(Common.zillinksApi("2258701327").toZillinksData())
                .logo("logo_path")
                .introFile("intro_file_path")
                .mediaKitFile("media_kit_file_path")
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        return introPage;
    }

    public IntroPage newIntroPage(User user) {
        IntroPage introPage = IntroPage.builder()
                .user(user)
                .webPageInfo(new WebPageInfo("pavicon_path", "webpage_name", "sub_domain", "title", "description"))
                .zillinksData(Common.zillinksApi("2258701327").toZillinksData())
                .logo("logo_path")
                .introFile("intro_file_path")
                .mediaKitFile("media_kit_file_path")
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        return introPage;
    }

    public IntroPage newMockIntroPage(Long id) {
        IntroPage introPage = IntroPage.builder()
                .webPageInfo(new WebPageInfo("pavicon_path" + id, "webpage_name" + id, "sub_domain" + id, "title" + id, "description" + id))
                .zillinksData(Common.zillinksApi("2258701327").toZillinksData())
                .logo("logo_path" + id)
                .introFile("intro_file_path" + id)
                .mediaKitFile("media_kit_file_path" + id)
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        return introPage;
    }
}
