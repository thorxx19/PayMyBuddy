package com.Pay.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transfer")
    private Long id;

    private String description;

    @Column(name = "balance")
    private BigDecimal amount;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_debtor")
    @NotNull
    private Profil idDebtor;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_credit")
    @NotNull
    private Profil idCredit;

    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transfer transfer = (Transfer) o;
        return id != null && Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
