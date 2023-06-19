package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetRequest;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetResponse;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.model.widget.*;
import com.fastcampus05.zillinks.domain.model.widget.repository.ProductsAndServicesElementQueryRepository;
import com.fastcampus05.zillinks.domain.model.widget.repository.ProductsAndServicesElementRepository;
import com.fastcampus05.zillinks.domain.model.widget.repository.TeamMemberElementQueryRepository;
import com.fastcampus05.zillinks.domain.model.widget.repository.TeamMemberElementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WidgetService {

    private final UserRepository userRepository;
    private final ProductsAndServicesElementRepository productsAndServicesElementRepository;
    private final ProductsAndServicesElementQueryRepository productsAndServicesElementQueryRepository;
    private final TeamMemberElementRepository teamMemberElementRepository;
    private final TeamMemberElementQueryRepository teamMemberElementQueryRepository;

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
        // Call To Action
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
        return WidgetResponse.UpdateProductsAndServicesOutDTO.toOutDTO(productsAndServicesPS, orderList);
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
        return WidgetResponse.SaveProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElementPS);
    }

    @Transactional
    public void deleteProductsAndServicesElements(WidgetRequest.DeleteProductsAndServicesElementsInDTO deleteProductsAndServicesElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        productsAndServicesElementQueryRepository.deleteByDeleteList(deleteProductsAndServicesElementsInDTO.getDeleteList());
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
        List<Long> orderList = new ArrayList<>();
        for (TeamMemberElement teamMemberElement : teamMemberElements) {
            orderList.add(teamMemberElement.getOrder());
        }
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
                        .build() : null
        );
        TeamMemberElement teamMemberElementPS = teamMemberElementRepository.save(teamMemberElement);
        return WidgetResponse.SaveTeamMemberElementOutDTO.toOutDTO(teamMemberElementPS);
    }

    @Transactional
    public void deleteTeamMemberElements(WidgetRequest.DeleteTeamMemberElementsInDTO deleteTeamMemberElementsInDTO, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        teamMemberElementQueryRepository.deleteByDeleteList(deleteTeamMemberElementsInDTO.getDeleteList());
    }


//    List<String> pathOrginList = new ArrayList<>();
//        pathOrginList.add(introPagePS.getSiteInfo().getPavicon());
//    List<String> pathList = new ArrayList<>();
//        pathList.add(updateSiteInfoInDTO.getPavicon());
//
//    manageS3Uploader(pathOrginList, pathList);

//    private void manageS3Uploader(List<String> pathOrginList, List<String> pathList) {
//        log.info("변경 전 pathOriginList={}", pathOrginList);
//        log.info("변경 후 pathList={}", pathList);
//        List<S3UploaderFile> s3UploaderFileListPS = s3UploaderFileRepository.findByEncodingPaths(pathOrginList).orElse(null);
//        log.info("ss3UploaderFileListPS={}", s3UploaderFileListPS);
//        for (String pathOrigin : pathOrginList) {
//            log.info("pathOrigin={}", pathOrigin);
//            if (!pathList.contains(pathOrigin) && !(pathOrigin == null)) {
//                s3UploaderFileRepository.delete(s3UploaderFileListPS.stream().filter(s -> s.getEncodingPath().equals(pathOrigin)).findAny().orElseThrow(
//                        () -> new Exception500("manageS3Uploader: 파일 관리에 문제가 생겼습니다.")
//                ));
//                s3UploaderRepository.delete(pathOrigin);
//            }
//        }
//    }
}
