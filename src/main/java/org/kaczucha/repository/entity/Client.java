package org.kaczucha;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

@Entity
@Table(name = "USERS")

public class Client {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String name;
    @Column(name = "MAIL")
    private String email;
    @Transient
    private double balance;

    private List<>

    public Client(String name, String email, double balance) {
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public Client() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.balance, balance) == 0 && Objects.equals(name, client.name) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, balance);
    }

}
