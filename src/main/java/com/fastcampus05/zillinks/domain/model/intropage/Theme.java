package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Theme {
    @Column(name = "theme_type")
    @Enumerated(EnumType.STRING)
    private ThemeType themeType;
    private String color;

    public void updateTheme(ThemeType themeType, String color) {
        this.themeType = themeType;
        this.color = color;
    }
}
