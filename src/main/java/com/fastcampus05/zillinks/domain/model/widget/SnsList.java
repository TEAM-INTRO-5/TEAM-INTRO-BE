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
    @Column(name = "instagram_status")
    private Boolean instagramStatus;
    private String instagram;
    @Column(name = "linked_in_status")
    private Boolean linkedInStatus;
    @Column(name = "linked_in")
    private String linkedIn;
    @Column(name = "youtube_status")
    private Boolean youtubeStatus;
    private String youtube;
    @Column(name = "notion_status")
    private Boolean notionStatus;
    private String notion;
    @Column(name = "naver_blog_status")
    private Boolean naverBlogStatus;
    @Column(name = "naver_blog")
    private String naverBlog;
    @Column(name = "brunch_stroy_status")
    private Boolean brunchStroyStatus;
    @Column(name = "brunch_stroy")
    private String brunchStroy;
    @Column(name = "facebook_status")
    private Boolean facebookStatus;
    private String facebook;
}
