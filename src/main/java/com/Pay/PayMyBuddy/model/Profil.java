package com.Pay.PayMyBuddy.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@RequiredArgsConstructor
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String mail;
    private String password;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_account")
    private Account accountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profil profil = (Profil) o;
        return id != null && Objects.equals(id, profil.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
