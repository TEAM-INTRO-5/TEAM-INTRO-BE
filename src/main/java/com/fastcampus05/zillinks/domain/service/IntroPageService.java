package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.intropage.CompanyInfo;
import com.fastcampus05.zillinks.domain.model.intropage.CompanyInfoRepository;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class IntroPageService {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    private final IntroPageRepository introPageRepository;
    private final UserRepository userRepository;
    private final S3UploaderFileRepository s3UploaderFileRepository;
    private final S3UploaderRepository s3UploaderRepository;

    @Transactional
    public SaveIntroPageOutDTO saveIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = introPageRepository.save(IntroPage.saveIntroPage(userPS));
        CompanyInfo companyInfo = CompanyInfo.saveCompanyInfo(introPagePS);
        companyInfo.setIntroPage(introPagePS);
        return SaveIntroPageOutDTO.builder()
                .color(introPagePS.getColor())
                .build();
    }

    public IntroPageOutDTO findIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        if (userPS.getIntroPage() == null)
            throw new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다.");

        return IntroPageOutDTO.builder()
                .color(userPS.getIntroPage().getColor())
                .build();
    }

    @Transactional
    public UpdateIntroPageOutDTO updateIntroPage(IntroPageRequest.UpdateInDTO updateInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        if (userPS.getIntroPage() == null)
            throw new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다.");

        userPS.getIntroPage().changeIntroPage(updateInDTO.getColor());
        /**
         * check-point
         * if (!introPagePS.getWebPageInfo().getPavicon().equals(updateInfoInDTO.getPavicon())) {
         *             Optional<S3UploaderFile> s3UploaderFileOP = s3UploaderFileRepository.findByEncodingPath(introPagePS.getWebPageInfo().getPavicon());
         *             s3UploaderFileOP.ifPresent(s3UploaderFileRepository::delete);
         *         }
         *         이와 같이 S3UploaderFile을 관리하는 로직이 추가되어야 한다.
         *
         *         이후 EncodingPath를 List로 받아 where encodingPath in (List) 이런식으로 처리하자.
         */
        return UpdateIntroPageOutDTO.builder()
                .color(userPS.getIntroPage().getColor())
                .build();
    }

    public InfoOutDTO findInfo(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        if (userPS.getIntroPage() == null)
            throw new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다.");

        return InfoOutDTO.builder()
                .pavicon(userPS.getIntroPage().getWebPageInfo().getPavicon())
                .webPageName(userPS.getIntroPage().getWebPageInfo().getWebPageName())
                .subDomain(userPS.getIntroPage().getWebPageInfo().getDomain())
                .title(userPS.getIntroPage().getWebPageInfo().getTitle())
                .description(userPS.getIntroPage().getWebPageInfo().getDescription())
                .build();
    }

    @Transactional
    public UpdateInfoOutDTO updateInfo(IntroPageRequest.UpdateInfoInDTO updateInfoInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        if (userPS.getIntroPage() == null)
            throw new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다.");

        // pavicon 저장된 경로 변경이 없을 경우 기존 데이터 사용으로 간주 - 삭제X
        // check-point 원하는대로 동작하는지 테스트 필요
        if (!userPS.getIntroPage().getWebPageInfo().getPavicon().equals(updateInfoInDTO.getPavicon())) {
            if (!userPS.getIntroPage().getWebPageInfo().getPavicon().equals(DEFAULT_IMAGE))
                s3UploaderRepository.delete(userPS.getIntroPage().getWebPageInfo().getPavicon());
            Optional<S3UploaderFile> s3UploaderFileOP = s3UploaderFileRepository.findByEncodingPath(userPS.getIntroPage().getWebPageInfo().getPavicon());
            s3UploaderFileOP.ifPresent(s3UploaderFileRepository::delete);
        }
        userPS.getIntroPage().changeIntroPageInfo(
                updateInfoInDTO.getPavicon(),
                updateInfoInDTO.getWebPageName(),
                updateInfoInDTO.getDomain(),
                updateInfoInDTO.getTitle(),
                updateInfoInDTO.getDescription()
        );
        return UpdateInfoOutDTO.builder()
                .pavicon(userPS.getIntroPage().getWebPageInfo().getPavicon())
                .webPageName(userPS.getIntroPage().getWebPageInfo().getWebPageName())
                .subDomain(userPS.getIntroPage().getWebPageInfo().getDomain())
                .title(userPS.getIntroPage().getWebPageInfo().getTitle())
                .description(userPS.getIntroPage().getWebPageInfo().getDescription())
                .build();
    }
}
