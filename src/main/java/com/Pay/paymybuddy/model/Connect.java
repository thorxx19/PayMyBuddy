package com.Pay.paymybuddy.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "connection")
public class Connect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_connection")
    private Long id;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_client_un")
    @NotNull
    private Profil idUn;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_client_deux")
    @NotNull
    private Profil idDeux;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Connect that = (Connect) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
