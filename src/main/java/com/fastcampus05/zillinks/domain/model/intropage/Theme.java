package com.fastcampus05.zillinks.domain.model.intropage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Theme {

    @Pattern(regexp = "ThemeA|ThemeB")
    private String type;
    private String color;
}
