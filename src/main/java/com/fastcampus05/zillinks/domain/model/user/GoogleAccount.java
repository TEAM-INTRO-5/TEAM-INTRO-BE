package com.fastcampus05.zillinks.domain.model.user;

import javax.persistence.*;

@Entity
public class GoogleAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "google_account_id")
    private Long id;

}
