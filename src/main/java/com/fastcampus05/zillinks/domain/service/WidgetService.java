package com.fastcampus05.zillinks.domain.service;

import com.fastcampus05.zillinks.core.exception.Exception400;
import com.fastcampus05.zillinks.core.exception.Exception500;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetRequest;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetResponse;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.user.UserRepository;
import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServices;
import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import com.fastcampus05.zillinks.domain.model.widget.Widget;
import com.fastcampus05.zillinks.domain.model.widget.repository.ProductsAndServicesElementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WidgetService {

    private final UserRepository userRepository;
    private final ProductsAndServicesElementRepository productsAndServicesElementRepository;

    @Transactional
    public WidgetResponse.SaveProductsAndServicesElementOutDTO saveProductsAndServicesElement(WidgetRequest.SaveProductsAndServicesElementInDTO saveProductsAndServicesElement, User user) {
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception400("id", "등록되지 않은 유저입니다."));

        IntroPage introPagePS = Optional.ofNullable(userPS.getIntroPage())
                .orElseThrow(() -> new Exception400("user_id", "해당 유저의 intro_page는 존재하지 않습니다."));

        Widget widget = introPagePS.getWidgets().stream().filter(s -> s instanceof ProductsAndServices).findFirst().orElseThrow(
                () -> new Exception500("ProductsAndServices 위젯이 존재하지 않습니다.")
        );
        ProductsAndServicesElement productsAndServicesElement = new ProductsAndServicesElement(
                (ProductsAndServices) widget,
                saveProductsAndServicesElement.getImage(),
                saveProductsAndServicesElement.getName(),
                saveProductsAndServicesElement.getTitle(),
                saveProductsAndServicesElement.getDescription()
        );
        ProductsAndServicesElement productsAndServicesElementPS = productsAndServicesElementRepository.save(productsAndServicesElement);
        productsAndServicesElementPS.setOrder(productsAndServicesElementPS.getId());
        return WidgetResponse.SaveProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElementPS);
    }
}
