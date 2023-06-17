package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("key_visual_and_slogan")
@Table(name = "key_visual_and_slogan_tb")
@SuperBuilder
@Slf4j
public class KeyVisualAndSlogan extends Widget {
    private String background;
    @Enumerated(EnumType.STRING)
    private Filter filter;
    private String slogan;
    @Column(name = "slogan_detail")
    private String sloganDetail;
}
