package com.fastcampus05.zillinks.domain.model.log.intropage;

import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DiscriminatorValue("contact_us_log")
public class ContactUsLog extends IntroPageLog {
    @NotNull
    private String content;
    @NotNull
    private String type;

    @Enumerated(EnumType.STRING)
    private ContactUsStatus contactUsStatus; // CANCEL, CONFIRM
}
