package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "site_info_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteInfo extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id", unique = true)
    private IntroPage introPage;

    @Column(unique = true)
    private String pavicon; // 경로

    @Column(name = "sub_domain", unique = true)
    private String subDomain;

    private String title;

    @Lob
    private String description;

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    public static SiteInfo saveSiteInfo() {
        return SiteInfo.builder().build();
    }

    public void updateSiteInfo(String pavicon, String subDomain, String title, String description) {
        this.pavicon = pavicon;
        this.subDomain = subDomain;
        this.title = title;
        this.description = description;
    }
}
