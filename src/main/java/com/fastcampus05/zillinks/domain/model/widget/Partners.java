package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("partners")
@Table(name = "partners_tb")
@SuperBuilder
@AllArgsConstructor
public class Partners extends Widget {
    @OneToMany(mappedBy = "partners", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnersElement> partnersElements = new ArrayList<>();
}
