package com.fastcampus05.zillinks.domain.model.dashboard;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("download_log")
@Table(name = "download_log_tb")
public class DownloadLog extends Dashboard {
    @NotNull
    private String type;

    @Builder
    public DownloadLog(IntroPage introPage, String type) {
        super(introPage);
        this.type = type;
    }
}
