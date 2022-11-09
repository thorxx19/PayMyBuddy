package com.Pay.PayMyBuddy.model;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@RequiredArgsConstructor
public class Profil {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "client_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotEmpty
    @Length(min = 5, max = 15)
    private String name;

    @Column(name = "last_name")
    @NotEmpty
    @Length(min = 5, max = 15)
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9- ]+\\.)+[a-zA-Z]{2,7}$", message = "Mail non valide")
    private String mail;

    @NotEmpty
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password non valide")
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
