package com.fastcampus05.zillinks.domain.model.log.intropage;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DiscriminatorValue("download_log")
public class DownloadLog extends IntroPageLog {
    @NotNull
    private String keyword;
}
