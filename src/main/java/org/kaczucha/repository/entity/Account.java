package org.kaczucha.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private long id;
    @Column(name = "BALANCE")
    private double balance;
    @Column(name = "CURRENCY")
    private String currency;

    public Account(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }
}
