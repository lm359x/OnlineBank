package com.lm359x.bank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name="balance")
    private Long Balance;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="active")
    private Boolean active;

    public Account(Long balance, AccountType accountType) {
        this.Balance = balance;
        this.accountType = accountType;
        this.active = false;
    }
    @OneToMany(mappedBy = "to")
    private List<Operation> inOperations;

    @OneToMany(mappedBy = "from")
    private List<Operation> outOperations;

    @Override
    public String toString() {
        return "Account{" +
                "Balance=" + Balance +
                ", accountType=" + accountType +
                ", inOperations=" + inOperations +
                ", outOperations=" + outOperations +
                '}';
    }
}
