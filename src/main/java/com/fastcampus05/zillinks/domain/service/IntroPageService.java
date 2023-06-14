package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.dashboard.*;
import com.fastcampus05.zillinks.domain.model.dashboard.repository.ContactUsLogRepository;
import com.fastcampus05.zillinks.domain.model.dashboard.repository.DownloadLogRepository;
import com.fastcampus05.zillinks.domain.model.intropage.*;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    private final ContactUsLogRepository contactUsLogRepository;
    private final DownloadLogRepository downloadLogRepository;

    @Transactional
    public void saveContactUs(IntroPageRequest.ContactUsInDTO contactUsInDTO) {
        IntroPage introPagePS = introPageRepository.findById(contactUsInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));
        contactUsLogRepository.save(ContactUsLog.builder()
                .introPage(introPagePS)
                .name(contactUsInDTO.getName())
                .email(contactUsInDTO.getEmail())
                .content(contactUsInDTO.getContent())
                .type(contactUsInDTO.getType())
                .contactUsStatus(ContactUsStatus.UNCONFIRMED)
                .build());
    }

    public void downloadFile(IntroPageRequest.DownloadFileInDTO downloadFileInDTO) {
        IntroPage introPagePS = introPageRepository.findById(downloadFileInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));

        String path = null;
        // check-point 위젯부분 추가후 진행
//        if (downloadFileInDTO.getType().equals("intro_file"))
//            path = introPagePS.getCompanyInfo().getIntroFile();
//        else if (downloadFileInDTO.getType().equals("media_kit_file"))
//            path = introPagePS.getCompanyInfo().getMediaKitFile();

        S3UploaderFile s3UploaderFilePS = s3UploaderFileRepository.findByEncodingPath(path)
                .orElseThrow(() -> new Exception400("type", "존재하지 않은 파일입니다."));

//        MultipartFile multipartFile = null;
//        try {
//            multipartFile = FIleUtil.urlToMultipartFile(path, s3UploaderFilePS.getOriginalPath());
//        } catch (IOException e) {
//            throw new Exception500("파일 변환에 실패하였습니다.\n" + e.getMessage());
//        }
//        // check-point 테스트 진행동안 잠시 막아둠
//        mailService.sendFile(downloadFileInDTO.getEmail(), introPagePS.getCompanyInfo().getCompanyName(), downloadFileInDTO.getType(), multipartFile);
        downloadLogRepository.save(DownloadLog.builder()
                .introPage(introPagePS)
                .downloadType(DownloadType.valueOf(downloadFileInDTO.getType()))
                .build());
    }

    @Transactional
    public void saveIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = introPageRepository.save(IntroPage.saveIntroPage(userPS));
        CompanyInfo companyInfo = CompanyInfo.saveCompanyInfo(introPagePS);
        companyInfo.setIntroPage(introPagePS);
    }

    public IntroPageOutDTO findIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        return IntroPageOutDTO.toOutDTO(introPagePS);
    }

    @Transactional
    public void updateIntroPage(IntroPageRequest.UpdateInDTO updateInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        if (updateInDTO.getStatus() && (introPagePS.getSiteInfo().getSubDomain() == null ||introPagePS.getSiteInfo().getSubDomain().isBlank()))
            throw new Exception400("status", "기본 주소가 설정되어 있지 않은 상태에서 회사 소개 페이지를 공개하실 수 없습니다.");

        // check-point
        // orderList를 활용하여 위젯의 순서를 변경하는 로직이 추가

        introPagePS.updateMainPage(updateInDTO.getStatus(), updateInDTO.getWidgetStatusList());
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
    }

    @Transactional
    public void updateCompanyInfo(IntroPageRequest.UpdateCompanyInfoInDTO updateCompanyInfoInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        CompanyInfo companyInfoPS = Optional.ofNullable(introPagePS.getCompanyInfo())
                .orElseThrow(() -> new Exception400("intro_page_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));

        // check-point 이후 바뀔 수도 있는 내용
//        CompanyInfo companyInfoPS = Optional.ofNullable(userPS.getCompanyInfo())
//                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));

        // pavicon 저장된 경로 변경이 없을 경우 기존 데이터 사용으로 간주 - 삭제X
        // check-point 원하는대로 동작하는지 테스트 필요
        List<String> pathOrginList = new ArrayList<>();
        pathOrginList.add(companyInfoPS.getLogo());

        List<String> pathList = new ArrayList<>();
        pathList.add(updateCompanyInfoInDTO.getLogo());

        manageS3Uploader(pathOrginList, pathList);

        companyInfoPS.updateCompanyInfo(
                updateCompanyInfoInDTO.getRepresentative(),
                updateCompanyInfoInDTO.getLogo(),
                updateCompanyInfoInDTO.getContactEmail(),
                updateCompanyInfoInDTO.getPhoneNumber(),
                updateCompanyInfoInDTO.getFaxNumber()
        );
    }

    @Transactional
    public void updateSiteInfo(IntroPageRequest.UpdateSiteInfoInDTO updateSiteInfoInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

//        if (introPagePS.getSaveStatus().equals(SaveStatus.OPEN) && updateSiteInfoInDTO.getSubDomain().isBlank()) {
//            // pavicon 경로에 대한 삭제 전에 throw가 이루어져야한다.
//            // S3 서버랑, FILE_TB에 이미 올라가 있는 상태일 것이다.
//            if (!introPagePS.getSiteInfo().getPavicon().equals(updateSiteInfoInDTO.getPavicon())) {
//                s3UploaderRepository.delete(updateSiteInfoInDTO.getPavicon());
//                s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(updateSiteInfoInDTO.getPavicon()).orElseThrow(
//                        () -> new Exception500("삭제해야할 파일을 찾지 못했습니다.")
//                ));
//            }
//        }
        // pavicon 저장된 경로 변경이 없을 경우 기존 데이터 사용으로 간주 - 삭제X
        // check-point 원하는대로 동작하는지 테스트 필요
        List<String> pathOrginList = new ArrayList<>();
        pathOrginList.add(introPagePS.getSiteInfo().getPavicon());
        List<String> pathList = new ArrayList<>();
        pathList.add(updateSiteInfoInDTO.getPavicon());

        manageS3Uploader(pathOrginList, pathList);

        // 저장은 무조건 되도록 하고 SiteInfo 영역에 대한 수정은 saveStatus를 숨김으로 바꾼다.
        log.info("updateSiteInfoInDTO.getSubDomain()={}", updateSiteInfoInDTO.getSubDomain());
        introPagePS.getSiteInfo().updateSiteInfo(
                updateSiteInfoInDTO.getPavicon(),
                updateSiteInfoInDTO.getSubDomain(),
                updateSiteInfoInDTO.getTitle(),
                updateSiteInfoInDTO.getDescription()
        );
        introPagePS.updateSaveStatus(SaveStatus.HIDDEN);
    }

    private void manageS3Uploader(List<String> pathOrginList, List<String> pathList) {
        log.info("변경 전 pathOriginList={}", pathOrginList);
        log.info("변경 후 pathList={}", pathList);
        List<S3UploaderFile> s3UploaderFileListPS = s3UploaderFileRepository.findByEncodingPaths(pathOrginList).orElse(null);
        log.info("ss3UploaderFileListPS={}", s3UploaderFileListPS);
        for (String pathOrigin : pathOrginList) {
            log.info("pathOrigin={}", pathOrigin);
            if (!pathList.contains(pathOrigin) && !(pathOrigin == null)) {
                s3UploaderFileRepository.delete(s3UploaderFileListPS.stream().filter(s -> s.getEncodingPath().equals(pathOrigin)).findAny().orElseThrow(
                        () -> new Exception500("manageS3Uploader: 파일 관리에 문제가 생겼습니다.")
                ));
                s3UploaderRepository.delete(pathOrigin);
            }
        }
    }

    public void checkSubDomain(String subDomain, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        Optional<IntroPage> introPagePS = introPageRepository.findByDomain(subDomain);
        if (introPagePS.isPresent())
            throw new Exception400("sub_domain", "이미 존재하는 서브도메인입니다.");
    }

    //    public InfoOutDTO findInfo(User user) {
//        User userPS = userRepository.findById(user.getId())
//                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
//
//        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
//                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
//
//        return InfoOutDTO.builder()
//                .pavicon(introPagePS.getWebPageInfo().getPavicon())
//                .webPageName(introPagePS.getWebPageInfo().getWebPageName())
//                .domain(introPagePS.getWebPageInfo().getDomain())
//                .title(introPagePS.getWebPageInfo().getTitle())
//                .description(introPagePS.getWebPageInfo().getDescription())
//                .build();
//    }

//    public CompanyInfoOutDTO findCompanyInfo(User user) {
//        User userPS = userRepository.findById(user.getId())
//                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
//
//        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
//                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
//
//        CompanyInfo companyInfoPS = Optional.ofNullable(introPagePS.getCompanyInfo())
//                .orElseThrow(() -> new Exception400("intro_page_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));
//
//
//        // check-point 이후 바뀔 수도 있는 내용
////        CompanyInfo companyInfoPS = Optional.ofNullable(userPS.getCompanyInfo())
////                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));
//
//        return CompanyInfoOutDTO.builder()
//                .companyName(companyInfoPS.getCompanyName())
//                .bizNum(companyInfoPS.getBizNum())
//                .contactEmail(companyInfoPS.getContactEmail())
//                .tagline(companyInfoPS.getTagline())
//                .logo(companyInfoPS.getLogo())
//                .introFile(companyInfoPS.getIntroFile())
//                .mediaKitFile(companyInfoPS.getMediaKitFile())
//                .build();
//    }
}
