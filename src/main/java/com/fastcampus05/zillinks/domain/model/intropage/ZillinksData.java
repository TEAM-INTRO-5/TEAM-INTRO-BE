package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ZillinksData {

    @NotEmpty
    private String name;

    @NotEmpty
    private String bizNum;

    @NotEmpty
    private String contactEmail;

    @Lob
    private String tagline;
}
