package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("patent")
@Table(name = "patent_tb")
public class Patent extends Widget {
    @OneToMany(mappedBy = "patent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatentElement> patentElements;
}
