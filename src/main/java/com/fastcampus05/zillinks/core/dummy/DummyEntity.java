package com.fastcampus05.zillinks.core.dummy;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.domain.model.intropage.*;
import com.fastcampus05.zillinks.domain.model.user.Marketing;
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
                .theme(new Theme(ThemeType.ThemeA, "#ffffff"))
                .introPageStatus(IntroPageStatus.valueOf("PRIVATE"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .startDate(zillinksData.getStartDate())
                .representative(zillinksData.getRepresentative())
                .logo(null)
                .contactEmail(zillinksData.getContactEmail())
                .bizNum(zillinksData.getBizNum())
                .phoneNumber("010-0000-0000")
                .faxNumber("010-0000-0000")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }

    public IntroPage newIntroPage(User user) {
        ZillinksData zillinksData = Common.zillinksApi("2258701327").toZillinksData();
        IntroPage introPage = IntroPage.builder()
                .user(user)
                .theme(new Theme(ThemeType.ThemeA, "#ffffff"))
                .introPageStatus(IntroPageStatus.valueOf("PRIVATE"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .startDate(zillinksData.getStartDate())
                .representative(zillinksData.getRepresentative())
                .logo(null)
                .contactEmail(zillinksData.getContactEmail())
                .bizNum(zillinksData.getBizNum())
                .phoneNumber("010-0000-0000")
                .faxNumber("010-0000-0000")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }

    public IntroPage newMockIntroPage(Long id) {
        ZillinksData zillinksData = Common.zillinksApi("2258701327").toZillinksData();
        IntroPage introPage = IntroPage.builder()
                .theme(new Theme(ThemeType.ThemeA, "#ffffff"))
                .introPageStatus(IntroPageStatus.valueOf("PRIVATE"))
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .startDate(zillinksData.getStartDate())
                .representative(zillinksData.getRepresentative())
                .logo("logo_path")
                .contactEmail(zillinksData.getContactEmail())
                .bizNum(zillinksData.getBizNum())
                .phoneNumber("010-0000-0000")
                .faxNumber("010-0000-0000")
                .build();
        companyInfo.setIntroPage(introPage);
        return introPage;
    }

    public S3UploaderFile newS3UploaderFile() {
        return S3UploaderFile.builder()
                .originalPath("default.jpg")
                .encodingPath("https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
                .build();
    }
}
