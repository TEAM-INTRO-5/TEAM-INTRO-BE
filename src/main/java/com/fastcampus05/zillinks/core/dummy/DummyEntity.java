package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.domain.model.intropage.*;
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
        ZillinksData zillinksData = Common.zillinksApi("2258701327").toZillinksData();
        IntroPage introPage = IntroPage.builder()
                .color("#ffffff")
                .webPageInfo(new WebPageInfo("pavicon_path", "web_page_name", "domain", "title", "description"))
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .bizNum(zillinksData.getBizNum())
                .contactEmail(zillinksData.getContactEmail())
                .tagline(zillinksData.getTagline())
                .logo("logo_path")
                .introFile("intro_file_path")
                .mediaKitFile("media_kit_file_path")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }

    public IntroPage newIntroPage(User user) {
        ZillinksData zillinksData = Common.zillinksApi("2258701327").toZillinksData();
        IntroPage introPage = IntroPage.builder()
                .user(user)
                .color("#ffffff")
                .webPageInfo(new WebPageInfo("pavicon_path", "web_page_name", "domain", "title", "description"))
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .bizNum(zillinksData.getBizNum())
                .contactEmail(zillinksData.getContactEmail())
                .tagline(zillinksData.getTagline())
                .logo("logo_path")
                .introFile("intro_file_path")
                .mediaKitFile("media_kit_file_path")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }

    public IntroPage newMockIntroPage(Long id) {
        ZillinksData zillinksData = Common.zillinksApi("2258701327").toZillinksData();
        IntroPage introPage = IntroPage.builder()
                .color("#ffffff")
                .webPageInfo(new WebPageInfo("pavicon_path", "web_page_name", "domain", "title", "description"))
                .saveStatus(SaveStatus.valueOf("SAVED"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .bizNum(zillinksData.getBizNum())
                .contactEmail(zillinksData.getContactEmail())
                .tagline(zillinksData.getTagline())
                .logo("logo_path")
                .introFile("intro_file_path")
                .mediaKitFile("media_kit_file_path")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }
}
