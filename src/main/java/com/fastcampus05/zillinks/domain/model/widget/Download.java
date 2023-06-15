package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("download")
@Table(name = "download_tb")
public class Download extends Widget {
    private String description;
    @Column(name = "media_kit_file")
    private String mediaKitFile;
    @Column(name = "intro_file")
    private String introFile;
}
