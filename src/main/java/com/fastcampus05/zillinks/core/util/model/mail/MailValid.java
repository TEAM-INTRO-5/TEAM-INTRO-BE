package com.fastcampus05.zillinks.core.util.model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash(value = "mailValid", timeToLive = 60 * 10)
@AllArgsConstructor
@Getter
public class MailValid {

    @Id
    private String code;

}
