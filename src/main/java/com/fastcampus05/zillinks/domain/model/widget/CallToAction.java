package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallToAction {
    private String description;
    private String text;
    private String link;

    public void updateCallToAction(String description, String text, String link) {
        this.description = description;
        this.text = text;
        this.link = link;
    }
}
