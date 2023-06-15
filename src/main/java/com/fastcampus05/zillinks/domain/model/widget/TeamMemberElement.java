package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team_member_element_tb")
public class TeamMemberElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

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
}
