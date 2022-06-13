package org.kaczucha.service;

import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.HibernateClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private BankService bankService;

    public static void main(String[] args) {
        new Main().run();

    }

    public void run() {
        ClientRepository repository = new HibernateClientRepository();
        bankService = new BankService(repository);
        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("1. Add user");
                System.out.println("2. Find user");
                System.out.println("3. Delete user");
                System.out.println("3. Exit");
                String next = scanner.next();
                if (Objects.equals(next, "1")) {
                    addUser(scanner);
                } else if (Objects.equals(next, "2")) {
                    printUser(scanner);
                } else if (Objects.equals(next, "3")) {
                    deleteUser(scanner);
                } else {
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteUser(Scanner scanner) {
        System.out.println("enter email");
        String mail = scanner.next();
        bankService.deleteClient(mail);
    }

    private void printUser(Scanner scanner) {
        System.out.println("enter email");
        String mail = scanner.next();
        System.out.println(bankService.findByEmail(mail));
    }

    private void addUser(Scanner scanner) throws SQLException {
        System.out.println("enter name");
        String name = scanner.next();
        System.out.println("enter email");
        String email = scanner.next();
        System.out.println("enter balance");
        Double balance = scanner.nextDouble();
        Account account = new Account(balance, "PLN");
        List<Account> accounts = List.of(account);
        bankService.save(new Client(name, email, accounts));
    }
}

