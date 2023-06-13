package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteInfo {
    @Column(unique = true)
    private String pavicon; // 경로

    @Column(unique = true)
    private String subDomain;

    private String title;

    @Lob
    private String description;

    public void updateSiteInfo(String pavicon, String subDomain, String title, String description) {
        this.pavicon = pavicon;
        this.subDomain = subDomain;
        this.title = title;
        this.description = description;
    }
}
