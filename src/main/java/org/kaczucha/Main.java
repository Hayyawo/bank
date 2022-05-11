package org.kaczucha;

import org.kaczucha.repository.InMemoryClientRepository;
import org.kaczucha.service.BankService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private BankService bankService;

    public static void main(String[] args) {
        new Main().run();

    }

    public void run() {
        InMemoryClientRepository repository = new InMemoryClientRepository(new ArrayList<>());
        bankService = new BankService(repository);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Add user");
                System.out.println("2. Find user");
                System.out.println("3. Exit");
                String next = scanner.next();
                if (Objects.equals(next, "1")) {
                    addUser(scanner);
                } else if (Objects.equals(next, "2")) {
                    printUser(scanner);
                } else {
                    break;
                }
            }
        }
    }

    private void printUser(Scanner scanner) {
        System.out.println("enter email");
        String  mail = scanner.next();
//        System.out.println(bankService.findByEmail(mail));
    }

    private void addUser(Scanner scanner) {
        System.out.println("enter name");
        String name = scanner.next();
        System.out.println("enter email");
        String email = scanner.next();
        System.out.println("enter balance");
        Double balance = scanner.nextDouble();
        bankService.save(new Client(name, email, balance));
    }
}

