package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("partners")
@Table(name = "partners_tb")
public class Partners extends Widget {
    @OneToMany(mappedBy = "partners", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnersElement> partnersElements;
}
