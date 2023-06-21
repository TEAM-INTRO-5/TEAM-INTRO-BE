package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.model.intropage.*;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.model.widget.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.IntroPageOutDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IntroPageService {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    private final IntroPageRepository introPageRepository;
    private final UserRepository userRepository;
    private final S3UploaderFileRepository s3UploaderFileRepository;
    private final S3UploaderRepository s3UploaderRepository;
    private final SiteInfoRepository siteInfoRepository;

    @Transactional
    public IntroPageResponse.FindIntroPageOutDTO getIntroPage(IntroPageRequest.FindIntroPageInDTO findIntroPageInDTO) {
        SiteInfo siteInfoPS = siteInfoRepository.findBySubDomain(findIntroPageInDTO.getSubDomain())
                .orElseThrow(() -> new Exception400("sub_domain", "존재하지 sub_domain입니다."));
        IntroPage introPagePS = siteInfoPS.getIntroPage();
        if (introPagePS.getIntroPageStatus().equals(IntroPageStatus.PRIVATE))
            throw new Exception400("intro_page_status", "비공개 상태의 회사 소개 페이지입니다.");

        List<Integer> orderList = new ArrayList<>();
        for (Widget widget : introPagePS.getWidgets()) {
            orderList.add(widget.getOrder());
        }

        return IntroPageResponse.FindIntroPageOutDTO.toOutDTO(introPagePS, orderList);
    }

    @Transactional
    public void saveIntroPage(IntroPageRequest.SaveIntroPageInDTO saveIntroPageInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = introPageRepository.save(IntroPage.saveIntroPage(userPS));
        introPagePS.setCompanyInfo(CompanyInfo.saveCompanyInfo(introPagePS));
        introPagePS.setSiteInfo(SiteInfo.saveSiteInfo());
        introPagePS.setHeaderAndFooter(HeaderAndFooter.saveHeaderAndFooter());

        List<WidgetType> widgetTypeList = saveIntroPageInDTO.getWidgetTypeList();
        System.out.println("widgetTypeList=" + widgetTypeList);
        int i = 1;
        for (WidgetType widgetType : widgetTypeList) {
            if (widgetType.equals(WidgetType.KEYVISUALANDSLOGAN))
                introPagePS.addWidgets(KeyVisualAndSlogan.builder().order(i).widgetStatus(true).widgetType(WidgetType.KEYVISUALANDSLOGAN).filter(Filter.BLACK).build());
            else if (widgetType.equals(WidgetType.MISSIONANDVISION))
                introPagePS.addWidgets(MissionAndVision.builder().order(i).widgetStatus(true).widgetType(WidgetType.MISSIONANDVISION).build());
            else if (widgetType.equals(WidgetType.PRODUCTSANDSERVICES))
                introPagePS.addWidgets(ProductsAndServices.builder().order(i).widgetStatus(true).widgetType(WidgetType.PRODUCTSANDSERVICES).callToActionStatus(false).build());
            else if (widgetType.equals(WidgetType.TEAMMEMBER))
                introPagePS.addWidgets(TeamMember.builder().order(i).widgetStatus(true).widgetType(WidgetType.TEAMMEMBER).build());
            else if (widgetType.equals(WidgetType.CONTACTUS))
                introPagePS.addWidgets(ContactUs.builder().order(i).widgetStatus(true).widgetType(WidgetType.CONTACTUS).mapStatus(false).build());
            else if (widgetType.equals(WidgetType.PERFORMANCE))
                introPagePS.addWidgets(Performance.builder().order(i).widgetStatus(true).widgetType(WidgetType.PERFORMANCE).build());
            else if (widgetType.equals(WidgetType.TEAMCULTURE))
                introPagePS.addWidgets(TeamCulture.builder().order(i).widgetStatus(true).widgetType(WidgetType.TEAMCULTURE).build());
            else if (widgetType.equals(WidgetType.HISTORY))
                introPagePS.addWidgets(History.builder().order(i).widgetStatus(true).widgetType(WidgetType.HISTORY).build());
            else if (widgetType.equals(WidgetType.REVIEW))
                introPagePS.addWidgets(Review.builder().order(i).widgetStatus(true).widgetType(WidgetType.REVIEW).build());
            else if (widgetType.equals(WidgetType.PATENT))
                introPagePS.addWidgets(Patent.builder().order(i).widgetStatus(true).widgetType(WidgetType.PATENT).build());
            else if (widgetType.equals(WidgetType.NEWS))
                introPagePS.addWidgets(News.builder().order(i).widgetStatus(true).widgetType(WidgetType.NEWS).build());
            else if (widgetType.equals(WidgetType.DOWNLOAD))
                introPagePS.addWidgets(Download.builder().order(i).widgetStatus(true).widgetType(WidgetType.DOWNLOAD).build());
            else if (widgetType.equals(WidgetType.PARTNERS))
                introPagePS.addWidgets(Partners.builder().order(i).widgetStatus(true).widgetType(WidgetType.PARTNERS).build());
            else if (widgetType.equals(WidgetType.CHANNEL))
                introPagePS.addWidgets(Channel.builder().order(i).widgetStatus(true).widgetType(WidgetType.CHANNEL).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.KEYVISUALANDSLOGAN)) {
            introPagePS.addWidgets(KeyVisualAndSlogan.builder().order(i).widgetStatus(false).widgetType(WidgetType.KEYVISUALANDSLOGAN).filter(Filter.BLACK).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.MISSIONANDVISION)) {
            introPagePS.addWidgets(MissionAndVision.builder().order(i).widgetStatus(false).widgetType(WidgetType.MISSIONANDVISION).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.PRODUCTSANDSERVICES)) {
            introPagePS.addWidgets(ProductsAndServices.builder().order(i).widgetStatus(false).widgetType(WidgetType.PRODUCTSANDSERVICES).callToActionStatus(false).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.TEAMMEMBER)) {
            introPagePS.addWidgets(TeamMember.builder().order(i).widgetStatus(false).widgetType(WidgetType.TEAMMEMBER).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.CONTACTUS)) {
            introPagePS.addWidgets(ContactUs.builder().order(i).widgetStatus(false).widgetType(WidgetType.CONTACTUS).mapStatus(false).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.PERFORMANCE)) {
            introPagePS.addWidgets(Performance.builder().order(i).widgetStatus(false).widgetType(WidgetType.PERFORMANCE).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.TEAMCULTURE)) {
            introPagePS.addWidgets(TeamCulture.builder().order(i).widgetStatus(false).widgetType(WidgetType.TEAMCULTURE).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.HISTORY)) {
            introPagePS.addWidgets(History.builder().order(i).widgetStatus(false).widgetType(WidgetType.HISTORY).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.REVIEW)) {
            introPagePS.addWidgets(Review.builder().order(i).widgetStatus(false).widgetType(WidgetType.REVIEW).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.PATENT)) {
            introPagePS.addWidgets(Patent.builder().order(i).widgetStatus(false).widgetType(WidgetType.PATENT).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.NEWS)) {
            introPagePS.addWidgets(News.builder().order(i).widgetStatus(false).widgetType(WidgetType.NEWS).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.DOWNLOAD)) {
            introPagePS.addWidgets(Download.builder().order(i).widgetStatus(false).widgetType(WidgetType.DOWNLOAD).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.PARTNERS)) {
            introPagePS.addWidgets(Partners.builder().order(i).widgetStatus(false).widgetType(WidgetType.PARTNERS).build());
            i++;
        }
        if (!widgetTypeList.contains(WidgetType.CHANNEL)) {
            introPagePS.addWidgets(Channel.builder().order(i).widgetStatus(false).widgetType(WidgetType.CHANNEL).build());
        }
    }

    public IntroPageOutDTO findIntroPage(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        List<Integer> orderList = new ArrayList<>();
        for (Widget widget : introPagePS.getWidgets()) {
            orderList.add(widget.getOrder());
        }
        return IntroPageOutDTO.toOutDTO(introPagePS, orderList);
    }

    @Transactional
    public void updateIntroPage(IntroPageRequest.UpdateInDTO updateInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        if (updateInDTO.getStatus() && (introPagePS.getSiteInfo().getSubDomain() == null ||introPagePS.getSiteInfo().getSubDomain().isBlank()))
            throw new Exception400("status", "기본 주소가 설정되어 있지 않은 상태에서 회사 소개 페이지를 공개하실 수 없습니다.");

        List<Widget> widgets = introPagePS.getWidgets();
//        for (int i = 0; i < widgets.size(); i++) {
//            widgets.get(i).setOrder(updateInDTO.getOrderList().get(i));
//        }
        Integer index = 1;

        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < updateInDTO.getOrderList().size(); i++)
            arr.add(0);

        for (Integer num : updateInDTO.getOrderList()) {
            Widget widget = widgets.stream().filter(s -> s.getOrder() == num).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = widgets.indexOf(widget);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            widgets.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateMainPage(updateInDTO.getStatus(), updateInDTO.getWidgetStatusList(), arr);
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
        log.info("test");
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
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    public void checkSubDomain(String subDomain, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        Optional<IntroPage> introPagePS = introPageRepository.findByDomain(subDomain);
        if (introPagePS.isPresent())
            throw new Exception400("sub_domain", "이미 존재하는 서브도메인입니다.");
    }

    @Transactional
    public void updateHeaderAndFooter(IntroPageRequest.UpdateHeaderAndFooter updateHeaderAndFooter, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        introPagePS.getHeaderAndFooter().updateHeaderAndFooter(updateHeaderAndFooter.getHeaderAndFooterStatusList());
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
