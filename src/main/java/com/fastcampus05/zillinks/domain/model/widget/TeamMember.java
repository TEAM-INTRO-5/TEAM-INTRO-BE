package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("team_member")
@Table(name = "team_member_tb")
@Builder
@AllArgsConstructor
public class TeamMember extends Widget {
    @OneToMany(mappedBy = "teamMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMemberElement> teamMemberElements = new ArrayList<>();
}
