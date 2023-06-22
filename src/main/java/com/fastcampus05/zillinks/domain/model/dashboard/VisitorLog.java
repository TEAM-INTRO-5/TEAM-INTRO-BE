package com.fastcampus05.zillinks.domain.model.dashboard;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("visitor_log")
@Table(name = "visitor_log_tb")
public class VisitorLog extends Dashboard {

    @NotNull
    @Column(name = "device_type")
    private String deviceType;

    private String keyword;

    @Column(name = "sharing_code")
    private String sharingCode;

    @Builder
    public VisitorLog(IntroPage introPage, String deviceType, String keyword, String sharingCode) {
        super(introPage);
        this.deviceType = deviceType;
        this.keyword = keyword;
        this.sharingCode = sharingCode;
    }
}
