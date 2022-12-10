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

    public Account(Long balance, AccountType accountType, User user) {
        Balance = balance;
        this.accountType = accountType;
        this.user = user;
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
