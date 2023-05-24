package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "intro_page_tb")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.JOInNED)
//@DiscriminatorColumn(name = "dtype")
public abstract class IntroPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intod_page_id")
    private Long id;

    @OneToOne
    private User user;

//    private 1번영역 -> aType, bType, cType;
    @NotEmpty
    private String companyName;

    @NotEmpty
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    @Lob
    private String description;

    @NotEmpty
    private String subDomain;

    private String analyticsCode;

    @Enumerated(EnumType.STRING)
    private SaveStatus status; // [TEMP_SAVED, SAVED, UPDATING]

//    private List<Widget> wigets;

    // 뭔지 더 알아봐야함, widget
    private String mission;

//    private ContactWidget contactWidget;
//    widget...


//    private FirstSector firstSector;
//    private SecondSector secondSector;
//    private ThirdSector thirdSector;

//    transaction.start
//    introPage.setFirstSector(BType);
//    transaction.end


//    @Inheritance(strategy = InheritanceType.Singled)
//    abstract class FirstSector {
//    }
//
//    class AType extends FirstSector {
//        private String blahblah;
//        private String blahbla2;
//    }
//
//    class BType extends FirstSector {
//        private String blahblah3;
//        private String blahbla4;
//    }
//
//    class CType extends FirstSector {
//        private String blahblah5;
//        private String blahbla6;
//    }

//    FirstSector sector = new AType(); // 1, 2
//    FirstSector sector = new BType();
}
