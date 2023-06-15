package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("contact_us")
@Table(name = "contact_us_tb")
public class ContactUs extends Widget {
    @Column(name = "map_status")
    private Boolean mapStatus;
    @Column(name = "full_address")
    private String fullAddress;
    @Column(name = "detailed_address")
    private String detailedAddress;
    private String latitude; // 위도
    private String longitude; // 경도
}