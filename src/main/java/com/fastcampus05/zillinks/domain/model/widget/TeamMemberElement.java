package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team_member_element_tb")
public class TeamMemberElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @Column(name = "orders")
    private Long order;

    private String profile;
    private String name;
    @Column(name = "groups")
    private String group;
    private String position;
    private String tagline;
    private String email;

    @Column(name = "sns_status")
    private Boolean snsStatus;
    @Embedded
    private SnsList snsList;

    public void setOrder(Long order) {
        this.order = order;
    }

    public TeamMemberElement(TeamMember teamMember, Long order, String profile, String name, String group, String position, String tagline, String email, Boolean snsStatus, SnsList snsList) {
        this.teamMember = teamMember;
        this.order = order;
        this.profile = profile;
        this.name = name;
        this.group = group;
        this.position = position;
        this.tagline = tagline;
        this.email = email;
        this.snsStatus = snsStatus;
        this.snsList = snsList;
    }
}
