package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String email;

    @NotEmpty
    @Column(length = 60)
    private String password;

    @Column(unique = true) // checkpoint
    private String businessNum;

    private String role; // USER|ADMIN

    // /{intropageId}/작업
    // -> Intropage -> user_id /JWT - id => 접근권한있다.

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Marketing> marketings = new ArrayList<>();

    //== 연관관계 메서드==//
    public void addMargeting(Marketing marketing) {
        marketings.add(marketing);
        marketing.setUser(this);
    }
}