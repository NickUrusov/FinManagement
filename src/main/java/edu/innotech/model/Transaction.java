package edu.innotech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tpp_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "sum_transaction")
    @NotNull
    private Double sumTransaction;

    @Column(name = "date_trans")
    @NotNull
    private Date dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_trans")
    @NotNull
    private TypeTransaction typeTransaction;

    @Column(name = "user_id")
    @NotNull
    private Long userId;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Transaction transaction)) return false;

        return Objects.equals(id, transaction.id);
    }


}
