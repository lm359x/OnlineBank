package com.lm359x.bank.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private Account from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Account to;

    @Column(name="amount")
    private Long amount;

    @Column(name="date_time")
    private Date dateTime;

    public Operation(Account from, Account to, Long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                '}';
    }
}
