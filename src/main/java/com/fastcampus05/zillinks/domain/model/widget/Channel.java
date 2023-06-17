package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("channel")
@Table(name = "channel_tb")
@SuperBuilder
@AllArgsConstructor
public class Channel extends Widget {
    @Embedded
    private SnsList snsList;
}
