package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SnsList {
    private String instagram;
    @Column(name = "linked_in")
    private String linkedIn;
    private String youtube;
    private String notion;
    @Column(name = "naver_blog")
    private String naverBlog;
    @Column(name = "brunch_stroy")
    private String brunchStroy;
    private String facebook;
}
