package org.kaczucha.service;

import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.entity.Client;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

/**
 * korzystając z TDD zaimplementuj metodę deleteClient(String email) która:
 * - usuwa klienta z repozytorium
 * - rzuca wyjątek w przypadku nieprawidłowego maila
 * - nie pozwala na null maila
 * - nie pozwala na usunięcie klienta jeżeli balance != 0
 */
public class BankService {
    private final ClientRepository clientRepository;


    public BankService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save(Client client) throws SQLException {
        clientRepository.save(client);
    }

    public void deleteClient(String email) {
        clientRepository.deleteClient(email);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }


    public void transfer(
            String fromEmail,
            String toEmail,
            double amount
    ) {

        if (amount < 0) {
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail cant be equal!");
        }
        Client fromClient = findByEmail(fromEmail);
        Client toClient = findByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0) {
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new IllegalArgumentException("Not enough funds!");
        }
    }

    public void withdraw(String email, double amount) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");

        }

        Client byEmail = clientRepository.findByEmail(email.toLowerCase())
                .orElseThrow(EntityNotFoundException::new);

        if (byEmail.getBalance() - amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw more than u got on ac");
        }
        byEmail.setBalance(byEmail.getBalance() - amount);
    }

}

