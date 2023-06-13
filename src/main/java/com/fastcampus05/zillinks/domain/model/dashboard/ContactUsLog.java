package com.fastcampus05.zillinks.domain.model.dashboard;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("contact_us_log")
@Table(name = "contact_us_log_tb")
public class ContactUsLog extends Dashboard {
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

    public void updateContactUsStatus(ContactUsStatus contactUsStatus) {
        this.contactUsStatus = contactUsStatus;
    }
}
