package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("performance")
@Table(name = "performance_tb")
@SuperBuilder
@AllArgsConstructor
public class Performance extends Widget {
    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceElement> performanceElements = new ArrayList<>();
}
