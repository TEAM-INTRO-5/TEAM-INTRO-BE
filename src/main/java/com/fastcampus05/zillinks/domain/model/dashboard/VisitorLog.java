package com.fastcampus05.zillinks.domain.model.dashboard;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Builder
@AllArgsConstructor
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
}
