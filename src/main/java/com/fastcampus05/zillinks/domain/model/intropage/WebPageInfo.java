package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPageInfo {
    @NotEmpty
    private String pavicon; // 경로
    @Column(name = "web_page_name")
    private String webPageName;
    private String domain;
    private String title;
    @Lob
    private String description;
}
