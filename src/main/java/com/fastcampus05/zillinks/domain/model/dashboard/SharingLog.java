package com.fastcampus05.zillinks.domain.model.dashboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("sharing_log")
@Table(name = "sharing_log_tb")
public class SharingLog extends VisitorLog{
    private String sharingNumber;
}
