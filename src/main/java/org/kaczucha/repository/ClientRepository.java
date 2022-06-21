package org.kaczucha.repository;

import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;

import java.sql.SQLException;
import java.util.Optional;

public interface ClientRepository {
    void save(Client client) throws SQLException;

    void saveAccount(Account account, int clientId);

    void deleteClient(String email);

//    Optional<Account> findById(int id);

    void deleteAccount(int accountId);

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean notExistsByEmail(String email);

    void withdraw(double amount, int accountId);

    void transfer(String emailFrom, String emailTo, double amount);


    void deposit(String email, double amount, int accountId);

    void modifyUserName(String email, String newName);

    void modifyUserEmail(String email, String newEmail);
}
