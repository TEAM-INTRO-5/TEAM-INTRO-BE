package com.fastcampus05.zillinks.domain.model.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class Marketing {
    @Column(name = "marketing_email")
    private Boolean marketingEmail;
    @Column(name = "marketing_email_date")
    private LocalDateTime marketingEmailDate;

    public void setMarketingEmail(Marketing userMarketing, Boolean requestMarketingEmail) {
        // 유저의 마케팅 동의 안 함이고, 동의함으로 수정하면, 시간 갱신
        log.info("test1");
        if(userMarketing.marketingEmail == Boolean.FALSE && requestMarketingEmail == Boolean.TRUE){
            this.marketingEmail = true;
        log.info("test2");
            this.marketingEmailDate = LocalDateTime.now();
        log.info("test3");
            // 동의한 상태로 변동이 없어서 갱신 안 함
        } else if (userMarketing.marketingEmail == Boolean.TRUE && requestMarketingEmail == Boolean.TRUE) {
        log.info("test4");
            this.marketingEmail = true;
        }else {
        log.info("test5");
            // 동의 안 함이라 갱신 안 함 - 동의 안 함의 시간도 갱신하려면 LocalDateTime.now(); 대입
            this.marketingEmail = false;
        }
    }

}
