package com.fastcampus05.zillinks.domain.model.log.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("contact_us_log")
public class ContactUsLog extends IntroPageLog {
    @NotNull
    private String content;
    @NotNull
    private String type;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private ContactUsStatus contactUsStatus; // CANCEL, CONFIRM

    @Builder
    public ContactUsLog(IntroPage introPage, String email, String content, String type, String name, ContactUsStatus contactUsStatus) {
        super(introPage, email);
        this.content = content;
        this.type = type;
        this.name = name;
        this.contactUsStatus = contactUsStatus;
    }
}
