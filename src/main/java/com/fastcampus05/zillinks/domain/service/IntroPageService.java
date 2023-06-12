package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.FIleUtil;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.core.util.service.mail.MailService;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.intropage.CompanyInfo;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageRepository;
import com.fastcampus05.zillinks.domain.model.log.intropage.*;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.*;
import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.FindContactUsOutDTO.*;

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
    private final ContactUsLogQueryRepository contactUsLogQueryRepository;
    private final DownloadLogRepository downloadLogRepository;
    private final MailService mailService;

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

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        return IntroPageOutDTO.builder()
                .color(introPagePS.getColor())
                .build();
    }

    @Transactional
    public UpdateIntroPageOutDTO updateIntroPage(IntroPageRequest.UpdateInDTO updateInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

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
                .color(introPagePS.getColor())
                .build();
    }

    public InfoOutDTO findInfo(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        return InfoOutDTO.builder()
                .pavicon(introPagePS.getWebPageInfo().getPavicon())
                .webPageName(introPagePS.getWebPageInfo().getWebPageName())
                .domain(introPagePS.getWebPageInfo().getDomain())
                .title(introPagePS.getWebPageInfo().getTitle())
                .description(introPagePS.getWebPageInfo().getDescription())
                .build();
    }

    @Transactional
    public UpdateInfoOutDTO updateInfo(IntroPageRequest.UpdateInfoInDTO updateInfoInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Optional<IntroPage> checkDomain = introPageRepository.findByDomain(updateInfoInDTO.getDomain());
        if (checkDomain.isPresent())
            throw new Exception400("domain", "이미 사용중인 도메인입니다.");

        // pavicon 저장된 경로 변경이 없을 경우 기존 데이터 사용으로 간주 - 삭제X
        // check-point 원하는대로 동작하는지 테스트 필요
        List<String> pathOrginList = new ArrayList<>();
        pathOrginList.add(introPagePS.getWebPageInfo().getPavicon());

        List<String> pathList = new ArrayList<>();
        pathList.add(updateInfoInDTO.getPavicon());

        manageS3Uploader(pathOrginList, pathList);

        introPagePS.changeIntroPageInfo(
                updateInfoInDTO.getPavicon(),
                updateInfoInDTO.getWebPageName(),
                updateInfoInDTO.getDomain(),
                updateInfoInDTO.getTitle(),
                updateInfoInDTO.getDescription()
        );
        return UpdateInfoOutDTO.builder()
                .pavicon(introPagePS.getWebPageInfo().getPavicon())
                .webPageName(introPagePS.getWebPageInfo().getWebPageName())
                .domain(introPagePS.getWebPageInfo().getDomain())
                .title(introPagePS.getWebPageInfo().getTitle())
                .description(introPagePS.getWebPageInfo().getDescription())
                .build();
    }

    public CompanyInfoOutDTO findCompanyInfo(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        CompanyInfo companyInfoPS = Optional.ofNullable(introPagePS.getCompanyInfo())
                .orElseThrow(() -> new Exception400("intro_page_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));


        // check-point 이후 바뀔 수도 있는 내용
//        CompanyInfo companyInfoPS = Optional.ofNullable(userPS.getCompanyInfo())
//                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 회사 기본 정보는 존재하지 않습니다."));

        return CompanyInfoOutDTO.builder()
                .companyName(companyInfoPS.getCompanyName())
                .bizNum(companyInfoPS.getBizNum())
                .contactEmail(companyInfoPS.getContactEmail())
                .tagline(companyInfoPS.getTagline())
                .logo(companyInfoPS.getLogo())
                .introFile(companyInfoPS.getIntroFile())
                .mediaKitFile(companyInfoPS.getMediaKitFile())
                .build();
    }

    @Transactional
    public UpdateCompanyInfoOutDTO updateCompanyInfo(IntroPageRequest.UpdateCompanyInfoInDTO updateCompanyInfoInDTO, User user) {
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
        pathOrginList.add(companyInfoPS.getIntroFile());
        pathOrginList.add(companyInfoPS.getMediaKitFile());

        List<String> pathList = new ArrayList<>();
        pathList.add(updateCompanyInfoInDTO.getLogo());
        pathList.add(updateCompanyInfoInDTO.getIntroFile());
        pathList.add(updateCompanyInfoInDTO.getMediaKitFile());

        manageS3Uploader(pathOrginList, pathList);

        companyInfoPS.changeCompanyInfo(
                updateCompanyInfoInDTO.getLogo(),
                updateCompanyInfoInDTO.getIntroFile(),
                updateCompanyInfoInDTO.getMediaKitFile()
        );
        return UpdateCompanyInfoOutDTO.builder()
                .companyName(companyInfoPS.getCompanyName())
                .bizNum(companyInfoPS.getBizNum())
                .contactEmail(companyInfoPS.getContactEmail())
                .tagline(companyInfoPS.getTagline())
                .logo(companyInfoPS.getLogo())
                .introFile(companyInfoPS.getIntroFile())
                .mediaKitFile(companyInfoPS.getMediaKitFile())
                .build();
    }

    @Transactional
    public void saveContactUs(IntroPageRequest.ContactUsInDTO contactUsInDTO) {
        IntroPage introPagePS = introPageRepository.findById(contactUsInDTO.getIntroPageId())
                .orElseThrow(() -> new Exception400("intro_page_id", "존재하지 않는 회사 소개 페이지입니다."));
        contactUsLogRepository.save(ContactUsLog.builder()
                .introPage(introPagePS)
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
        if (downloadFileInDTO.getType().equals("intro_file"))
            path = introPagePS.getCompanyInfo().getIntroFile();
        else if (downloadFileInDTO.getType().equals("media_kit_file"))
            path = introPagePS.getCompanyInfo().getMediaKitFile();

        S3UploaderFile s3UploaderFilePS = s3UploaderFileRepository.findByEncodingPath(path)
                .orElseThrow(() -> new Exception400("type", "존재하지 않은 파일입니다."));

        MultipartFile multipartFile = null;
        try {
            multipartFile = FIleUtil.urlToMultipartFile(path, s3UploaderFilePS.getOriginalPath());
        } catch (IOException e) {
            throw new Exception500("파일 변환에 실패하였습니다.\n" + e.getMessage());
        }
        mailService.sendFile(downloadFileInDTO.getEmail(), introPagePS.getCompanyInfo().getCompanyName(), downloadFileInDTO.getType(), multipartFile);
        downloadLogRepository.save(DownloadLog.builder()
                .introPage(introPagePS)
                .email(downloadFileInDTO.getEmail())
                .keyword(downloadFileInDTO.getKeyword())
                .build());
    }

    public FindContactUsOutDTO findContactUs(ContactUsStatus status, Integer page, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Page<ContactUsLog> contactUsLogPG = contactUsLogQueryRepository.findAllByStatus(status, introPagePS.getId(), page);
        List<ContactUsOutDTO> contactUsOutDTOList = contactUsLogPG.stream()
                .map(s -> ContactUsOutDTO.builder()
                        .email(s.getEmail())
                        .content(s.getContent())
                        .type(s.getType())
                        .date(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return FindContactUsOutDTO.builder()
                .introPageId(introPagePS.getId())
                .content(contactUsOutDTOList)
                .totalElements(contactUsLogPG.getTotalElements())
                .totalPage(contactUsLogPG.getTotalPages())
                .size(contactUsLogPG.getSize())
                .number(contactUsLogPG.getNumber())
                .numberOfElements(contactUsLogPG.getNumberOfElements())
                .hasPrevious(contactUsLogPG.hasPrevious())
                .hasNext(contactUsLogPG.hasNext())
                .isFirst(contactUsLogPG.isFirst())
                .isLast(contactUsLogPG.isLast())
                .build();
    }

    private void manageS3Uploader(List<String> pathOrginList, List<String> pathList) {
        log.info("변경 전 pathOriginList={}", pathOrginList);
        log.info("변경 후 pathList={}", pathList);
        List<S3UploaderFile> s3UploaderFileListPS = s3UploaderFileRepository.findByEncodingPaths(pathOrginList).orElse(null);
        log.info("ss3UploaderFileListPS={}", s3UploaderFileListPS);
        for (String pathOrigin : pathOrginList) {
            if (!pathList.contains(pathOrigin)) {
                s3UploaderFileRepository.delete(s3UploaderFileListPS.stream().filter(s -> s.getEncodingPath().equals(pathOrigin)).findAny().orElseThrow(
                        () -> new Exception500("파일 관리에 문제가 생겼습니다.")
                ));
                s3UploaderRepository.delete(pathOrigin);
            }
        }
    }
}
