package com.Pay.PayMyBuddy.model;


import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String name;
    @Column(name = "last_name")
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String mail;
    @NotNull
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
}
