package com.fastcampus05.zillinks.domain.model.dashboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("view_log")
@Table(name = "view_log_tb")
public class ViewLog extends VisitorLog{
    @NotNull
    private String keyword;
}
