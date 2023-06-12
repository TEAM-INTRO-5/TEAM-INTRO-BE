package com.fastcampus05.zillinks.domain.model.log.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("download_log")
public class DownloadLog extends IntroPageLog {
    @NotNull
    private String type;

    @Builder
    public DownloadLog(IntroPage introPage, String email, String type) {
        super(introPage, email);
        this.type = type;
    }
}
