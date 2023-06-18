package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @NotNull
    @Column(name = "mission_and_vision")
    private Boolean misionAndVision;
    @Column(name = "products_and_services")
    @NotNull
    private Boolean productsAndServices;
    @NotNull
    @Column(name = "team_member")
    private Boolean teamMember;
    @NotNull
    @Column(name = "contact_us")
    private Boolean contactUs;
    @NotNull
    private Boolean news;
    @NotNull
    private Boolean download;
    @NotNull
    private Boolean history;
    @NotNull
    @Column(name = "team_culture")
    private Boolean teamCulture;
    @NotNull
    private Boolean performance;
    @NotNull
    private Boolean partners;
    @NotNull
    private Boolean review;
    @NotNull
    private Boolean patent;
    @NotNull
    private Boolean footer;

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    public void updateHeaderAndFooter(List<Boolean> booleanList) {
        this.misionAndVision = booleanList.get(0);
        this.productsAndServices = booleanList.get(1);
        this.teamMember = booleanList.get(2);
        this.contactUs = booleanList.get(3);
        this.news = booleanList.get(4);
        this.download = booleanList.get(5);
        this.history = booleanList.get(6);
        this.teamCulture = booleanList.get(7);
        this.performance = booleanList.get(8);
        this.partners = booleanList.get(9);
        this.review = booleanList.get(10);
        this.patent = booleanList.get(11);
        this.footer = booleanList.get(12);
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
