package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("history")
@Table(name = "history_tb")
@SuperBuilder
@AllArgsConstructor
public class History extends Widget {
    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoryElement> historyElements = new ArrayList<>();
}
