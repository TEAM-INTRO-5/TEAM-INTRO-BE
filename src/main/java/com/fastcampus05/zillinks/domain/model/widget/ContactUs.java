package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("contact_us")
@SuperBuilder
@AllArgsConstructor
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

    public void updateContactUsWidget(Boolean mapStatus, String fullAddress, String detailedAddress, String latitude, String longitude) {
        this.mapStatus = mapStatus;
        this.fullAddress = fullAddress;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setMapStatus(Boolean mapStatus) {
        this.mapStatus = mapStatus;
    }
}
