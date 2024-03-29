package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.dto.map.KakaoAddress;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFile;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderFileRepository;
import com.fastcampus05.zillinks.core.util.model.s3upload.S3UploaderRepository;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetRequest;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetResponse;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageStatus;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.model.widget.*;
import com.fastcampus05.zillinks.domain.model.widget.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WidgetService {

    private final S3UploaderFileRepository s3UploaderFileRepository;
    private final S3UploaderRepository s3UploaderRepository;
    private final UserRepository userRepository;
    private final ProductsAndServicesElementRepository productsAndServicesElementRepository;
    private final ProductsAndServicesElementQueryRepository productsAndServicesElementQueryRepository;
    private final TeamMemberElementRepository teamMemberElementRepository;
    private final TeamMemberElementQueryRepository teamMemberElementQueryRepository;
    private final PerformanceElementRepository performanceElementRepository;
    private final PerformanceElementQueryRepository performanceElementQueryRepository;
    private final TeamCultureElementRepository teamCultureElementRepository;
    private final TeamCultureElementQueryRepository teamCultureElementQueryRepository;
    private final HistoryElementRepository historyElementRepository;
    private final HistoryElementQueryRepository historyElementQueryRepository;
    private final ReviewElementRepository reviewElementRepository;
    private final ReviewElementQueryRepository reviewElementQueryRepository;
    private final PatentElementRepository patentElementRepository;
    private final PatentElementQueryRepository patentElementQueryRepository;
    private final NewsElementRepository newsElementRepository;
    private final NewsElementQueryRepository newsElementQueryRepository;
    private final PartnersElementRepository partnersElementRepository;
    private final PartnersElementQueryRepository partnersElementQueryRepository;

    private final String KAKAO_MAP_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Transactional
    public WidgetResponse.UpdateProductsAndServicesOutDTO updateProductsAndServices(WidgetRequest.UpdateProductsAndServicesInDTO updateProductsAndServicesInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        ProductsAndServices productsAndServicesPS = (ProductsAndServices) introPagePS.getWidgets().stream().filter(s -> s instanceof ProductsAndServices).findFirst().orElseThrow(
                () -> new Exception500("ProductsAndServices 위젯이 존재하지 않습니다.")
        );
        List<ProductsAndServicesElement> productsAndServicesElements = productsAndServicesPS.getProductsAndServicesElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updateProductsAndServicesInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updateProductsAndServicesInDTO.getOrderList()) {
            ProductsAndServicesElement productsAndServicesElementPS = productsAndServicesElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = productsAndServicesElements.indexOf(productsAndServicesElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            productsAndServicesElements.get(i).setOrder(arr.get(i));
        }
        List<Long> orderList = new ArrayList<>();
        for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesElements) {
            orderList.add(productsAndServicesElement.getOrder());
        }
        productsAndServicesPS.setWidgetStatus(updateProductsAndServicesInDTO.getWidgetStatus());
        // Call To Action
        if (updateProductsAndServicesInDTO.getWidgetStatus()) {
            productsAndServicesPS.setCallToActionStatus(updateProductsAndServicesInDTO.getCallToActionStatus());
            if (updateProductsAndServicesInDTO.getCallToActionStatus()) {
                if (productsAndServicesPS.getCallToAction() == null) {
                    productsAndServicesPS.setCallToAction(new CallToAction(
                            updateProductsAndServicesInDTO.getDescription(),
                            updateProductsAndServicesInDTO.getText(),
                            updateProductsAndServicesInDTO.getLink()
                    ));
                } else {
                    productsAndServicesPS.getCallToAction().updateCallToAction(
                            updateProductsAndServicesInDTO.getDescription(),
                            updateProductsAndServicesInDTO.getText(),
                            updateProductsAndServicesInDTO.getLink()
                    );
                }
            }
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateProductsAndServicesOutDTO.toOutDTO(productsAndServicesPS);
    }

    @Transactional
    public WidgetResponse.SaveProductsAndServicesElementOutDTO saveProductsAndServicesElement(WidgetRequest.SaveProductsAndServicesElementInDTO saveProductsAndServicesElement, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        ProductsAndServices productsAndServicesPS = (ProductsAndServices) introPagePS.getWidgets().stream().filter(s -> s instanceof ProductsAndServices).findFirst().orElseThrow(
                () -> new Exception500("ProductsAndServices 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesPS.getProductsAndServicesElements()) {
            index = Math.max(index, productsAndServicesElement.getOrder());
        }
        ProductsAndServicesElement productsAndServicesElement = new ProductsAndServicesElement(
                productsAndServicesPS,
                index + 1,
                saveProductsAndServicesElement.getImage(),
                saveProductsAndServicesElement.getName(),
                saveProductsAndServicesElement.getTitle(),
                saveProductsAndServicesElement.getDescription()
        );
        ProductsAndServicesElement productsAndServicesElementPS = productsAndServicesElementRepository.save(productsAndServicesElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElementPS);
    }

    @Transactional
    public void deleteProductsAndServicesElements(WidgetRequest.DeleteProductsAndServicesElementsInDTO deleteProductsAndServicesElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<ProductsAndServicesElement> productsAndServicesElements = productsAndServicesElementQueryRepository.findAllByDeleteList(deleteProductsAndServicesElementsInDTO.getDeleteList());
        for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesElements) {
            String image = productsAndServicesElement.getImage();
            if (image == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(image).orElseThrow(
                    () -> new Exception500("deleteProductsAndServicesElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(image);
        }

        productsAndServicesElementQueryRepository.deleteByDeleteList(deleteProductsAndServicesElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    @Transactional
    public WidgetResponse.UpdateTeamMemberOutDTO updateTeamMember(WidgetRequest.UpdateTeamMemberInDTO updateTeamMemberInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        TeamMember teamMemberPS = (TeamMember) introPagePS.getWidgets().stream().filter(s -> s instanceof TeamMember).findFirst().orElseThrow(
                () -> new Exception500("TeamMember 위젯이 존재하지 않습니다.")
        );
        List<TeamMemberElement> teamMemberElements = teamMemberPS.getTeamMemberElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updateTeamMemberInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updateTeamMemberInDTO.getOrderList()) {
            TeamMemberElement teamMemberElementPS = teamMemberElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = teamMemberElements.indexOf(teamMemberElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            teamMemberElements.get(i).setOrder(arr.get(i));
        }
        teamMemberPS.setWidgetStatus(updateTeamMemberInDTO.getWidgetStatus());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateTeamMemberOutDTO.toOutDTO(teamMemberPS);
    }

    @Transactional
    public WidgetResponse.SaveTeamMemberElementOutDTO saveTeamMemberElement(WidgetRequest.SaveTeamMemberElementInDTO saveTeamMemberElement, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        TeamMember teamMemberPS = (TeamMember) introPagePS.getWidgets().stream().filter(s -> s instanceof TeamMember).findFirst().orElseThrow(
                () -> new Exception500("TeamMember 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (TeamMemberElement teamMemberElement : teamMemberPS.getTeamMemberElements()) {
            index = Math.max(index, teamMemberElement.getOrder());
        }
        TeamMemberElement teamMemberElement = new TeamMemberElement(
                teamMemberPS,
                index + 1,
                saveTeamMemberElement.getProfile(),
                saveTeamMemberElement.getName(),
                saveTeamMemberElement.getGroup(),
                saveTeamMemberElement.getPosition(),
                saveTeamMemberElement.getTagline(),
                saveTeamMemberElement.getEmail(),
                saveTeamMemberElement.getSnsStatus(),
                saveTeamMemberElement.getSnsStatus() ? SnsList.builder()
                        .instagramStatus(saveTeamMemberElement.getInstagramStatus())
                        .instagram(saveTeamMemberElement.getInstagram())
                        .linkedInStatus(saveTeamMemberElement.getLinkedInStatus())
                        .linkedIn(saveTeamMemberElement.getLinkedIn())
                        .youtubeStatus(saveTeamMemberElement.getYoutubeStatus())
                        .youtube(saveTeamMemberElement.getYoutube())
                        .notionStatus(saveTeamMemberElement.getNotionStatus())
                        .notion(saveTeamMemberElement.getNotion())
                        .naverBlogStatus(saveTeamMemberElement.getNaverBlogStatus())
                        .naverBlog(saveTeamMemberElement.getNaverBlog())
                        .brunchStroyStatus(saveTeamMemberElement.getBrunchStroyStatus())
                        .brunchStroy(saveTeamMemberElement.getBrunchStroy())
                        .facebookStatus(saveTeamMemberElement.getFacebookStatus())
                        .facebook(saveTeamMemberElement.getFacebook())
                        .build() :
                        SnsList.builder()
                                .instagramStatus(false)
                                .linkedInStatus(false)
                                .youtubeStatus(false)
                                .notionStatus(false)
                                .naverBlogStatus(false)
                                .brunchStroyStatus(false)
                                .facebookStatus(false)
                                .build()
        );
        TeamMemberElement teamMemberElementPS = teamMemberElementRepository.save(teamMemberElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveTeamMemberElementOutDTO.toOutDTO(teamMemberElementPS);
    }

    @Transactional
    public void deleteTeamMemberElements(WidgetRequest.DeleteTeamMemberElementsInDTO deleteTeamMemberElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<TeamMemberElement> teamMemberElements = teamMemberElementQueryRepository.findAllByDeleteList(deleteTeamMemberElementsInDTO.getDeleteList());
        for (TeamMemberElement teamMemberElement : teamMemberElements) {
            String profile = teamMemberElement.getProfile();
            if (profile == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(profile).orElseThrow(
                    () -> new Exception500("deleteTeamMemberElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(profile);
        }

        teamMemberElementQueryRepository.deleteByDeleteList(deleteTeamMemberElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    /**
     * 핵심 성과
     */
    @Transactional
    public WidgetResponse.UpdatePerformanceOutDTO updatePerformance(WidgetRequest.UpdatePerformanceInDTO updatePerformanceInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Performance performancePS = (Performance) introPagePS.getWidgets().stream().filter(s -> s instanceof Performance).findFirst().orElseThrow(
                () -> new Exception500("Performance 위젯이 존재하지 않습니다.")
        );
        List<PerformanceElement> performanceElements = performancePS.getPerformanceElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updatePerformanceInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updatePerformanceInDTO.getOrderList()) {
            PerformanceElement performanceElementPS = performanceElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = performanceElements.indexOf(performanceElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            performanceElements.get(i).setOrder(arr.get(i));
        }
        performancePS.setWidgetStatus(updatePerformanceInDTO.getWidgetStatus());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdatePerformanceOutDTO.toOutDTO(performancePS);
    }

    @Transactional
    public WidgetResponse.SavePerformanceElementOutDTO savePerformanceElement(WidgetRequest.SavePerformanceElementInDTO savePerformanceElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Performance performancePS = (Performance) introPagePS.getWidgets().stream().filter(s -> s instanceof Performance).findFirst().orElseThrow(
                () -> new Exception500("Performance 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (PerformanceElement performanceElement : performancePS.getPerformanceElements()) {
            index = Math.max(index, performanceElement.getOrder());
        }
        PerformanceElement performanceElement = PerformanceElement.builder()
                .performance(performancePS)
                .order(index + 1)
                .descrition(savePerformanceElementInDTO.getDescrition())
                .additionalDescrition(savePerformanceElementInDTO.getAdditionalDescrition())
                .indicator(savePerformanceElementInDTO.getIndicator())
                .build();
        PerformanceElement performanceElementPS = performanceElementRepository.save(performanceElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SavePerformanceElementOutDTO.toOutDTO(performanceElementPS);
    }

    @Transactional
    public void deletePerformanceElements(WidgetRequest.DeletePerformanceElementsInDTO deletePerformanceElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        performanceElementQueryRepository.deleteByDeleteList(deletePerformanceElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    @Transactional
    public WidgetResponse.ContactUsOutDTO saveContactUs(WidgetRequest.ContactUsInDTO contactUsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        ContactUs contactUsPS = (ContactUs) introPagePS.getWidgets().stream().filter(s -> s instanceof ContactUs).findFirst().orElseThrow(
                () -> new Exception500("ContactUs 위젯이 존재하지 않습니다."));

        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);

        contactUsPS.setWidgetStatus(contactUsInDTO.getWidgetStatus());
        if (contactUsInDTO.getWidgetStatus()) {
            contactUsPS.setMapStatus(contactUsInDTO.getMapStatus());
            if (contactUsInDTO.getMapStatus()) {
                KakaoAddress kakaoAddress = Common.kakaoSearchAddress(KAKAO_MAP_URL, HttpMethod.GET, contactUsInDTO.getFullAddress());
                contactUsPS.updateContactUsWidget(contactUsInDTO.getMapStatus(),
                        contactUsInDTO.getFullAddress(),
                        contactUsInDTO.getDetailedAddress(),
                        kakaoAddress.getDocuments().get(0).getY(),
                        kakaoAddress.getDocuments().get(0).getX());
            }
        }
        return WidgetResponse.ContactUsOutDTO.toOutDTO(contactUsPS);
    }

    /**
     * 팀 컬려
     */
    @Transactional
    public WidgetResponse.UpdateTeamCultureOutDTO updateTeamCulture(WidgetRequest.UpdateTeamCultureInDTO updateTeamCultureInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        TeamCulture teamCulturePS = (TeamCulture) introPagePS.getWidgets().stream().filter(s -> s instanceof TeamCulture).findFirst().orElseThrow(
                () -> new Exception500("TeamCulture 위젯이 존재하지 않습니다.")
        );

        teamCulturePS.setWidgetStatus(updateTeamCultureInDTO.getWidgetStatus());

        List<TeamCultureElement> teamCultureElements = teamCulturePS.getTeamCultureElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updateTeamCultureInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updateTeamCultureInDTO.getOrderList()) {
            TeamCultureElement teamCultureElementPS = teamCultureElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = teamCultureElements.indexOf(teamCultureElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            teamCultureElements.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateTeamCultureOutDTO.toOutDTO(teamCulturePS);
    }

    @Transactional
    public WidgetResponse.SaveTeamCultureElementOutDTO saveTeamCultureElement(WidgetRequest.SaveTeamCultureElementInDTO saveTeamCultureElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        TeamCulture teamCulturePS = (TeamCulture) introPagePS.getWidgets().stream().filter(s -> s instanceof TeamCulture).findFirst().orElseThrow(
                () -> new Exception500("TeamCulture 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (TeamCultureElement teamCultureElement : teamCulturePS.getTeamCultureElements()) {
            index = Math.max(index, teamCultureElement.getOrder());
        }
        TeamCultureElement teamCultureElement = TeamCultureElement.builder()
                .teamCulture(teamCulturePS)
                .order(index + 1)
                .image(saveTeamCultureElementInDTO.getImage())
                .culture(saveTeamCultureElementInDTO.getCulture())
                .desciption(saveTeamCultureElementInDTO.getDescription())
                .build();
        TeamCultureElement teamCultureElementPS = teamCultureElementRepository.save(teamCultureElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveTeamCultureElementOutDTO.toOutDTO(teamCultureElementPS);
    }

    @Transactional
    public void deleteTeamCultureElements(WidgetRequest.DeleteTeamCultureElementsInDTO deleteTeamCultureElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<TeamCultureElement> teamCultureElements = teamCultureElementQueryRepository.findAllByDeleteList(deleteTeamCultureElementsInDTO.getDeleteList());
        for (TeamCultureElement teamCultureElement : teamCultureElements) {
            String image = teamCultureElement.getImage();
            if (image == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(image).orElseThrow(
                    () -> new Exception500("deleteTeamCultureElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(image);
        }

        teamCultureElementQueryRepository.deleteByDeleteList(deleteTeamCultureElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    @Transactional
    public WidgetResponse.KeyVisualAndSloganOutDTO saveKeyVisualAndSlogan(WidgetRequest.KeyVisualAndSloganInDTO keyVisualAndSloganInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        KeyVisualAndSlogan keyVisualAndSloganPS = (KeyVisualAndSlogan) introPagePS.getWidgets().stream().filter(s -> s instanceof KeyVisualAndSlogan).findFirst().orElseThrow(
                () -> new Exception500("KeyVisualAndSlogan 위젯이 존재하지 않습니다."));

        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);

        keyVisualAndSloganPS.setWidgetStatus(keyVisualAndSloganInDTO.getWidgetStatus());
        if (keyVisualAndSloganInDTO.getWidgetStatus()) {
            List<String> pathOrginList = new ArrayList<>();
            pathOrginList.add(keyVisualAndSloganPS.getBackground());
            List<String> pathList = new ArrayList<>();
            pathList.add(keyVisualAndSloganInDTO.getBackground());

            manageS3Uploader(pathOrginList, pathList);

            keyVisualAndSloganPS.updateKeyVisualAndSlogan(
                    keyVisualAndSloganInDTO.getBackground(),
                    keyVisualAndSloganInDTO.getFilter(),
                    keyVisualAndSloganInDTO.getSlogan(),
                    keyVisualAndSloganInDTO.getSloganDetail()
            );
        }
        return WidgetResponse.KeyVisualAndSloganOutDTO.toOutDTO(keyVisualAndSloganPS);
    }

    @Transactional
    public WidgetResponse.MissionAndVisionOutDTO saveMissionAndVision(WidgetRequest.MissionAndVisionInDTO missionAndVisionInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        MissionAndVision missionAndVisionPS = (MissionAndVision) introPagePS.getWidgets().stream().filter(s -> s instanceof MissionAndVision).findFirst().orElseThrow(
                () -> new Exception500("MissionAndVision 위젯이 존재하지 않습니다."));

        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);

        missionAndVisionPS.setWidgetStatus(missionAndVisionInDTO.getWidgetStatus());
        if (missionAndVisionInDTO.getWidgetStatus()) {
            missionAndVisionPS.updateMissionAndVision(
                    missionAndVisionInDTO.getMission(),
                    missionAndVisionInDTO.getMissionDetail(),
                    missionAndVisionInDTO.getVision(),
                    missionAndVisionInDTO.getVisionDetail()
            );
        }
        return WidgetResponse.MissionAndVisionOutDTO.toOutDTO(missionAndVisionPS);
    }

    /**
     * 연혁
     */
    @Transactional
    public WidgetResponse.UpdateHistoryOutDTO updateHistory(WidgetRequest.UpdateHistoryInDTO updateHistoryInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        History historyPS = (History) introPagePS.getWidgets().stream().filter(s -> s instanceof History).findFirst().orElseThrow(
                () -> new Exception500("History 위젯이 존재하지 않습니다.")
        );

        historyPS.setWidgetStatus(updateHistoryInDTO.getWidgetStatus());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateHistoryOutDTO.toOutDTO(historyPS);
    }

    @Transactional
    public WidgetResponse.SaveHistoryElementOutDTO saveHistoryElement(WidgetRequest.SaveHistoryElementInDTO saveHistoryElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        History historyPS = (History) introPagePS.getWidgets().stream().filter(s -> s instanceof History).findFirst().orElseThrow(
                () -> new Exception500("History 위젯이 존재하지 않습니다.")
        );

        HistoryElement historyElement = HistoryElement.builder()
                .history(historyPS)
                .image(saveHistoryElementInDTO.getImage())
                .date(saveHistoryElementInDTO.getDate())
                .title(saveHistoryElementInDTO.getTitle())
                .description(saveHistoryElementInDTO.getDescription())
                .build();
        HistoryElement historyElementPS = historyElementRepository.save(historyElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveHistoryElementOutDTO.toOutDTO(historyElementPS);
    }

    public void deleteHistoryElements(WidgetRequest.DeleteHistoryElementsInDTO deleteHistoryElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        historyElementQueryRepository.deleteByDeleteList(deleteHistoryElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    /**
     * 고객 리뷰
     */
    @Transactional
    public WidgetResponse.UpdateReviewOutDTO updateReview(WidgetRequest.UpdateReviewInDTO updateReviewInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Review reviewPS = (Review) introPagePS.getWidgets().stream().filter(s -> s instanceof Review).findFirst().orElseThrow(
                () -> new Exception500("Review 위젯이 존재하지 않습니다.")
        );

        reviewPS.setWidgetStatus(updateReviewInDTO.getWidgetStatus());

        List<ReviewElement> reviewElements = reviewPS.getReviewElement();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updateReviewInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updateReviewInDTO.getOrderList()) {
            ReviewElement reviewElementPS = reviewElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = reviewElements.indexOf(reviewElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            reviewElements.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateReviewOutDTO.toOutDTO(reviewPS);
    }

    @Transactional
    public WidgetResponse.SaveReviewElementOutDTO saveReviewElement(WidgetRequest.SaveReviewElementInDTO saveReviewElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Review reviewPS = (Review) introPagePS.getWidgets().stream().filter(s -> s instanceof Review).findFirst().orElseThrow(
                () -> new Exception500("Review 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (ReviewElement reviewElement : reviewPS.getReviewElement()) {
            index = Math.max(index, reviewElement.getOrder());
        }
        ReviewElement reviewElement = ReviewElement.builder()
                .review(reviewPS)
                .order(index + 1)
                .image(saveReviewElementInDTO.getImage())
                .name(saveReviewElementInDTO.getName())
                .group(saveReviewElementInDTO.getGroup())
                .rating(saveReviewElementInDTO.getRating())
                .details(saveReviewElementInDTO.getDetails())
                .build();
        ReviewElement reviewElementPS = reviewElementRepository.save(reviewElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveReviewElementOutDTO.toOutDTO(reviewElementPS);
    }

    @Transactional
    public void deleteReivewElements(WidgetRequest.DeleteReviewElementsInDTO deleteReviewElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<ReviewElement> reviewElements = reviewElementQueryRepository.findAllByDeleteList(deleteReviewElementsInDTO.getDeleteList());
        for (ReviewElement reviewElement : reviewElements) {
            String image = reviewElement.getImage();
            if (image == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(image).orElseThrow(
                    () -> new Exception500("deleteReivewElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(image);
        }
        reviewElementQueryRepository.deleteByDeleteList(deleteReviewElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    @Transactional
    public WidgetResponse.DownloadOutDTO saveDownload(WidgetRequest.DownloadInDTO downloadInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Download downloadPS = (Download) introPagePS.getWidgets().stream().filter(s -> s instanceof Download).findFirst().orElseThrow(
                () -> new Exception500("Download 위젯이 존재하지 않습니다."));

        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);

        downloadPS.setWidgetStatus(downloadInDTO.getWidgetStatus());
        if (downloadInDTO.getWidgetStatus()) {

            List<String> pathOrginList = new ArrayList<>();
            pathOrginList.add(downloadPS.getMediaKitFile());
            pathOrginList.add(downloadPS.getIntroFile());
            List<String> pathList = new ArrayList<>();
            pathList.add(downloadPS.getMediaKitFile());
            pathList.add(downloadPS.getIntroFile());

            manageS3Uploader(pathOrginList, pathList);

            downloadPS.updateDownload(
                    downloadInDTO.getDescription(),
                    downloadInDTO.getMediaKitFile(),
                    downloadInDTO.getIntroFile()
            );
        }
        return WidgetResponse.DownloadOutDTO.toOutDTO(downloadPS);
    }


    private void manageS3Uploader(List<String> pathOrginList, List<String> pathList) {
        log.info("변경 전 pathOriginList={}", pathOrginList);
        log.info("변경 후 pathList={}", pathList);
        List<S3UploaderFile> s3UploaderFileListPS = s3UploaderFileRepository.findByEncodingPaths(pathOrginList).orElse(null);
        log.info("ss3UploaderFileListPS={}", s3UploaderFileListPS);
        for (String pathOrigin : pathOrginList) {
            log.info("pathOrigin={}", pathOrigin);
            if (!pathList.contains(pathOrigin) && !(pathOrigin == null || pathOrigin.isEmpty())) {
                s3UploaderFileRepository.delete(s3UploaderFileListPS.stream().filter(s -> s.getEncodingPath().equals(pathOrigin)).findAny().orElseThrow(
                        () -> new Exception500("manageS3Uploader: 파일 관리에 문제가 생겼습니다.")
                ));
                s3UploaderRepository.delete(pathOrigin);
            }
        }
    }

    /**
     * 특허/인증
     */
    @Transactional
    public WidgetResponse.UpdatePatentOutDTO updatePatent(WidgetRequest.UpdatePatentInDTO updatePatentInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Patent patentPS = (Patent) introPagePS.getWidgets().stream().filter(s -> s instanceof Patent).findFirst().orElseThrow(
                () -> new Exception500("Patent 위젯이 존재하지 않습니다.")
        );

        patentPS.setWidgetStatus(updatePatentInDTO.getWidgetStatus());

        List<PatentElement> patentElements = patentPS.getPatentElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updatePatentInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updatePatentInDTO.getOrderList()) {
            PatentElement patentElementPS = patentElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = patentElements.indexOf(patentElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            patentElements.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdatePatentOutDTO.toOutDTO(patentPS);
    }

    @Transactional
    public WidgetResponse.SavePatentElementOutDTO savePatentElement(WidgetRequest.SavePatentElementInDTO savePatentElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Patent patentPS = (Patent) introPagePS.getWidgets().stream().filter(s -> s instanceof Patent).findFirst().orElseThrow(
                () -> new Exception500("Patent 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (PatentElement patentElement : patentPS.getPatentElements()) {
            index = Math.max(index, patentElement.getOrder());
        }
        PatentElement patentElement = PatentElement.builder()
                .patent(patentPS)
                .order(index + 1)
                .patentType(savePatentElementInDTO.getPatentType())
                .title(savePatentElementInDTO.getTitle())
                .image(savePatentElementInDTO.getImage())
                .build();
        PatentElement patentElementPS = patentElementRepository.save(patentElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SavePatentElementOutDTO.toOutDTO(patentElementPS);
    }

    @Transactional
    public void deletePatentElements(WidgetRequest.DeletePatentElementsInDTO deletePatentElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<PatentElement> patentElements = patentElementQueryRepository.findAllByDeleteList(deletePatentElementsInDTO.getDeleteList());
        for (PatentElement patentElement : patentElements) {
            String image = patentElement.getImage();
            if (image == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(image).orElseThrow(
                    () -> new Exception500("deletePatentElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(image);
        }
        patentElementQueryRepository.deleteByDeleteList(deletePatentElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    /**
     * 보고 자료
     */
    public WidgetResponse.ImportNewsOutDTO importNews(String url, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        NewsElement newsElement = Common.importNews(url);
        return WidgetResponse.ImportNewsOutDTO.toOutDTO(newsElement);
    }

    @Transactional
    public WidgetResponse.UpdateNewsOutDTO updateNews(WidgetRequest.UpdateNewsInDTO updateNewsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        News newsPS = (News) introPagePS.getWidgets().stream().filter(s -> s instanceof News).findFirst().orElseThrow(
                () -> new Exception500("News 위젯이 존재하지 않습니다.")
        );

        newsPS.setWidgetStatus(updateNewsInDTO.getWidgetStatus());

        List<NewsElement> newsElements = newsPS.getNewsElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updateNewsInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updateNewsInDTO.getOrderList()) {
            NewsElement newsElementPS = newsElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = newsElements.indexOf(newsElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            newsElements.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdateNewsOutDTO.toOutDTO(newsPS);
    }

    @Transactional
    public WidgetResponse.SaveNewsElementOutDTO saveNewsElement(WidgetRequest.SaveNewsElementInDTO saveNewsElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        News newsPS = (News) introPagePS.getWidgets().stream().filter(s -> s instanceof News).findFirst().orElseThrow(
                () -> new Exception500("News 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (NewsElement newsElement : newsPS.getNewsElements()) {
            index = Math.max(index, newsElement.getOrder());
        }
        NewsElement newsElement = NewsElement.builder()
                .news(newsPS)
                .order(index + 1)
                .image(saveNewsElementInDTO.getImage())
                .date(saveNewsElementInDTO.getDate())
                .press(saveNewsElementInDTO.getPress())
                .title(saveNewsElementInDTO.getTitle())
                .description(saveNewsElementInDTO.getDescription())
                .build();
        NewsElement newsElementPS = newsElementRepository.save(newsElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SaveNewsElementOutDTO.toOutDTO(newsElementPS);
    }

    @Transactional
    public void deleteNewsElements(WidgetRequest.DeleteNewsElementsInDTO deleteNewsElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        newsElementQueryRepository.deleteByDeleteList(deleteNewsElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    /**
     * 파트너스
     */
    @Transactional
    public WidgetResponse.UpdatePartnersOutDTO updatePartners(WidgetRequest.UpdatePartnersInDTO updatePartnersInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Partners partnersPS = (Partners) introPagePS.getWidgets().stream().filter(s -> s instanceof Partners).findFirst().orElseThrow(
                () -> new Exception500("Partners 위젯이 존재하지 않습니다.")
        );

        partnersPS.setWidgetStatus(updatePartnersInDTO.getWidgetStatus());

        List<PartnersElement> partnersElements = partnersPS.getPartnersElements();
        Long index = 1L;

        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < updatePartnersInDTO.getOrderList().size(); i++)
            arr.add(0L);

        for (Long aLong : updatePartnersInDTO.getOrderList()) {
            PartnersElement partnersElementPS = partnersElements.stream().filter(s -> s.getOrder() == aLong).findFirst().orElseThrow(
                    () -> new Exception400("order_list", "해당 order에 맞는 요소가 없습니다.")
            );
            int pos = partnersElements.indexOf(partnersElementPS);
            arr.set(pos, index);
            index++;
        }
        for (int i = 0; i < arr.size(); i++) {
            partnersElements.get(i).setOrder(arr.get(i));
        }
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.UpdatePartnersOutDTO.toOutDTO(partnersPS);
    }

    @Transactional
    public WidgetResponse.SavePartnersElementOutDTO savePartnersElement(WidgetRequest.SavePartnersElementInDTO savePartnersElementInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));
        Partners partnersPS = (Partners) introPagePS.getWidgets().stream().filter(s -> s instanceof Partners).findFirst().orElseThrow(
                () -> new Exception500("Partners 위젯이 존재하지 않습니다.")
        );
        long index = 0;
        for (PartnersElement partnersElement : partnersPS.getPartnersElements()) {
            index = Math.max(index, partnersElement.getOrder());
        }
        PartnersElement partnersElement = PartnersElement.builder()
                .partners(partnersPS)
                .order(index + 1)
                .partnersType(savePartnersElementInDTO.getPartnersType())
                .companyName(savePartnersElementInDTO.getCompanyName())
                .logo(savePartnersElementInDTO.getLogo())
                .build();
        PartnersElement partnersElementPS = partnersElementRepository.save(partnersElement);
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
        return WidgetResponse.SavePartnersElementOutDTO.toOutDTO(partnersElementPS);
    }

    @Transactional
    public void deletePartnersElements(WidgetRequest.DeletePartnersElementsInDTO deletePartnersElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));
        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        List<PartnersElement> partnersElements = partnersElementQueryRepository.findAllByDeleteList(deletePartnersElementsInDTO.getDeleteList());
        for (PartnersElement partnersElement : partnersElements) {
            String logo = partnersElement.getLogo();
            if (logo == null)
                continue;
            s3UploaderFileRepository.delete(s3UploaderFileRepository.findByEncodingPath(logo).orElseThrow(
                    () -> new Exception500("deletePartnersElements: 파일 관리 실패")
            ));
            s3UploaderRepository.delete(logo);
        }
        partnersElementQueryRepository.deleteByDeleteList(deletePartnersElementsInDTO.getDeleteList());
        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);
    }

    @Transactional
    public WidgetResponse.ChannelOutDTO saveChannel(WidgetRequest.ChannelInDTO channelInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Channel channelPS = (Channel) introPagePS.getWidgets().stream().filter(s -> s instanceof Channel).findFirst().orElseThrow(
                () -> new Exception500("Channel 위젯이 존재하지 않습니다."));

        introPagePS.updateSaveStatus(IntroPageStatus.PRIVATE);

        SnsList snsList = new SnsList(
                channelInDTO.getInstagramStatus(),
                channelInDTO.getInstagram(),
                channelInDTO.getLinkedInStatus(),
                channelInDTO.getLinkedIn(),
                channelInDTO.getYoutubeStatus(),
                channelInDTO.getYoutube(),
                channelInDTO.getNotionStatus(),
                channelInDTO.getNotion(),
                channelInDTO.getNaverBlogStatus(),
                channelInDTO.getNaverBlog(),
                channelInDTO.getBrunchStroyStatus(),
                channelInDTO.getBrunchStroy(),
                channelInDTO.getFacebookStatus(),
                channelInDTO.getFacebook()
        );

        channelPS.setWidgetStatus(channelInDTO.getWidgetStatus());
        if (channelInDTO.getWidgetStatus()) {
            channelPS.updateChannel(snsList);
        }
        return WidgetResponse.ChannelOutDTO.toOutDTO(channelPS.getSnsList());
    }

    public List<TeamMemberElement> test(User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        TeamMember teamMember = (TeamMember) introPagePS.getWidgets().stream().filter(s -> s instanceof TeamMember).findFirst().orElseThrow(
                () -> new Exception500("TeamMember 위젯이 존재하지 않습니다."));

        return teamMemberElementRepository.findAllByTeamMember(teamMember);
    }
}
