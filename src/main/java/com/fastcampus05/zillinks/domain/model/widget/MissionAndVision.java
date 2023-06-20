package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("mission_and_vision")
@Table(name = "mission_and_vision_tb")
public class MissionAndVision extends Widget {
    private String mission;
    @Column(name = "mission_detail")
    private String missionDetail;
    private String vision;
    @Column(name = "vision_detail")
    private String visionDetail;

    public void updateMissionAndVision(String mission, String missionDetail, String vision, String visionDetail) {
        this.mission = mission;
        this.missionDetail = missionDetail;
        this.vision = vision;
        this.visionDetail = visionDetail;
    }
}
