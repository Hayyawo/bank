package org.kaczucha.service;

import org.kaczucha.repository.InMemoryClientRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.HibernateClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    private BankService bankService;
    private InMemoryClientRepository memoryClientRepository;

    public static void main(String[] args) {
        new Main().run();

    }

    /*
     * funkcjonalnosc logowania
     * wyplacania / wplacania
     * client imie i nazwisko
     * */
    public void run() {
        ClientRepository repository = new HibernateClientRepository();
        bankService = new BankService(repository);
        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("1. Add user");
                System.out.println("2. Find user");
                System.out.println("3. Delete user");
                System.out.println("4. Modify user");
                System.out.println("5. Account");
                System.out.println("0. Exit");


                String next = scanner.next();

                if (Objects.equals(next, "1")) {
                    addUser(scanner);
                } else if (Objects.equals(next, "2")) {
                    printUser(scanner);
                } else if (Objects.equals(next, "3")) {
                    deleteUser(scanner);
                } else if (Objects.equals(next, "4")) {

                    while (true) {


                        System.out.println("1. Modify user name");
                        System.out.println("2. Modify user mail");
                        System.out.println("0. Exit");
                        String next1 = scanner.next();

                        System.out.println("Type searching user by e-mail");
                        String searchingMail = scanner.next();
                        System.out.println(repository.findByEmail(searchingMail));

                        if (Objects.equals(next1, "1")) {
                            System.out.println("New name");
                            String newName = scanner.next();
                            bankService.modifyUserName(searchingMail, newName);
                            System.out.println(repository.findByEmail(searchingMail));

                        }
                        if (Objects.equals(next1, "2")) {
                            System.out.println("new Email");
                            String newEmail = scanner.next();
                            bankService.modifyUserEmail(searchingMail, newEmail);

                        } else if (Objects.equals(next1, "0")) {
                            break;
                        }
                    }

                } else if (Objects.equals(next, "5")) {
                    while (true) {
                        System.out.println("Search accounts by email that belongs to them");
                        String searchingMail = scanner.next();
                        Optional<Client> byEmail = repository.findByEmail(searchingMail);
                        List<Account> accounts = byEmail.get().getAccounts();
                        System.out.println("Following user got those accounts");
                        System.out.println("User name " + byEmail.get().getName() + "\n" + accounts);

                        System.out.println("1. Deposit funds");
                        System.out.println("2. Withdraw funds");
                        System.out.println("3. Transfer funds");
                        System.out.println("4. Add account to following user ");
                        System.out.println("5. Delete account from following user");
                        System.out.println("0. Exit");
                        String next1 = scanner.next();


                        if (Objects.equals(next1, "1")) {
                            System.out.println();
                        } else if (Objects.equals(next1, "2")) {
                            System.out.println("Type email which you wont withdraw from");
                            String fromEmail = scanner.next();
                            System.out.println("Type amount");
                            double amount = scanner.nextDouble();
                            System.out.println("Type acc id");
                            int accountId = scanner.nextInt();
                            bankService.withdraw(fromEmail, amount, accountId);

                        } else if (Objects.equals(next1, "3")) {
                            System.out.println("Type email which you wont transfer from");
                            String fromEmail = scanner.next();
                            System.out.println("Type email which you wont transfer to");
                            String toEmail = scanner.next();
                            System.out.println("Type amount");
                            double amount = scanner.nextDouble();


                            bankService.transfer(fromEmail, toEmail, amount);
                        } else if (Objects.equals(next1, "4")) {

                        } else if (Objects.equals(next1, "5")) {

                        } else {
                            break;
                        }


                    }
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

