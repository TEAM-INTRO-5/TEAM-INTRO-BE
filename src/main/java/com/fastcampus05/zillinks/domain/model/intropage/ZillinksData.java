package com.fastcampus05.zillinks.domain.model.intropage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ZillinksData {

    @NotEmpty
    private String name;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String representative;

    @NotEmpty
    private String contactEmail;

    @NotEmpty
    private String bizNum;
}
