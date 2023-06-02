package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.intropage.ZillinksData;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class IntroPageService {

    private final IntroPageRepository introPageRepository;
    private final UserRepository userRepository;

    // check-point AutoSave일 때 어떻게 동작할지 생각해봐야 할 듯
    @Transactional
    public IntroPageResponse.SaveOutDTO createIntroPage(IntroPageRequest.SaveInDTO saveInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));

        // checkpoint, RestTemplate로 zillinksData 받아오기
        ZillinksData zillinksData = Common.zillinksApi(userPS.getBizNum()).toZillinksData();

        // check-point 스프링에서 trackingCode 자동할당 구현 -> 실력 부족으로 구현 불가능
        String trackingCode = null;
        IntroPage introPagePS = introPageRepository.save(saveInDTO.toEntity(userPS, zillinksData, trackingCode));
        introPagePS.mapTrackingCode(trackingCode);
        userPS.mapIntroPage(introPagePS);

        return IntroPageResponse.SaveOutDTO.builder()
                .id(introPagePS.getId())
                .pavicon(introPagePS.getPavicon())
                .webPageName(introPagePS.getWebPageName())
                .subDomain(introPagePS.getSubDomain())
                .title(introPagePS.getTitle())
                .description(introPagePS.getDescription())
                .zillinksData(introPagePS.getZillinksData())
                .logo(introPagePS.getLogo())
                .introFile(introPagePS.getIntroFile())
                .mediaKitFile(introPagePS.getMediaKitFile())
                .trackingCode(introPagePS.getTrackingCode())
                .build();
    }

    public IntroPageResponse.FindOutDTO findIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = userPS.getIntroPage();
        return IntroPageResponse.FindOutDTO.builder()
                .id(introPagePS.getId())
                .zillinksData(introPagePS.getZillinksData())
                .logo(introPagePS.getLogo())
                .introFile(introPagePS.getIntroFile())
                .mediaKitFile(introPagePS.getMediaKitFile())
                .trackingCode(introPagePS.getTrackingCode())
                .build();
    }

    @Transactional
    public IntroPageResponse.UpdateOutDTO updateIntroPage(IntroPageRequest.UpdateInDTO updateInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("email", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = userPS.getIntroPage();
        introPagePS.changeIntroPageInfo(updateInDTO);

        return IntroPageResponse.UpdateOutDTO.builder()
                .id(introPagePS.getId())
                .zillinksData(introPagePS.getZillinksData())
                .logo(introPagePS.getLogo())
                .introFile(introPagePS.getIntroFile())
                .mediaKitFile(introPagePS.getMediaKitFile())
                .trackingCode(introPagePS.getTrackingCode())
                .build();
    }
}
