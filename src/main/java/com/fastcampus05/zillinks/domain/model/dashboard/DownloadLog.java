package com.fastcampus05.zillinks.domain.model.dashboard;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("download_log")
@Table(name = "download_log_tb")
public class DownloadLog extends Dashboard {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "download_type")
    private DownloadType downloadType;

    @Builder
    public DownloadLog(IntroPage introPage, DownloadType downloadType) {
        super(introPage);
        this.downloadType = downloadType;
    }
}
