package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "header_and_footer_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeaderAndFooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "header_and_footer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id", unique = true)
    private IntroPage introPage;

    @Column(name = "mission_and_vision")
    private Boolean misionAndVision;
    @Column(name = "products_and_services")
    private Boolean productsAndServices;
    @Column(name = "team_member")
    private Boolean teamMember;
    @Column(name = "contact_us")
    private Boolean contactUs;
    private Boolean news;
    private Boolean download;
    private Boolean history;
    @Column(name = "team_culture")
    private Boolean teamCulture;
    private Boolean performance;
    private Boolean partners;
    private Boolean review;
    private Boolean patent;
    private Boolean footer;

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    public static HeaderAndFooter saveHeaderAndFooter() {
        return HeaderAndFooter.builder()
                .misionAndVision(false)
                .productsAndServices(false)
                .teamMember(false)
                .contactUs(false)
                .news(false)
                .download(false)
                .history(false)
                .teamCulture(false)
                .performance(false)
                .partners(false)
                .review(false)
                .patent(false)
                .footer(false)
                .build();
    }
}
