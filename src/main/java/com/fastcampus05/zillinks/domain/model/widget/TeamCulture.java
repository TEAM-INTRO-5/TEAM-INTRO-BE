package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("team_culture")
@Table(name = "team_culture_tb")
@Builder
@AllArgsConstructor
public class TeamCulture extends Widget {
    @OneToMany(mappedBy = "teamCulture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamCultureElement> teamCultureElements = new ArrayList<>();
}
