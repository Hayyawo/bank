package org.kaczucha.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")

@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String name;
    @Column(name = "MAIL")
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<Account> accounts;

    public Client(String name, String email, List<Account> accounts) {
        this.name = name;
        this.email = email;
        this.accounts = accounts;
    }

    public double getBalance() {
        if (!accounts.isEmpty()) {
            return accounts.get(0).getBalance();
        }
        return 0;
    }

    public void setBalance(double newBalance) {
        if (!accounts.isEmpty())
            accounts.get(0).setBalance(newBalance);
    }
}

