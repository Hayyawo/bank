package org.kaczucha.repository;

import org.kaczucha.repository.entity.Client;

import java.sql.SQLException;
import java.util.Optional;

public interface ClientRepository  {
    void save(Client client) throws SQLException;

    void deleteClient(String email);

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean notExistsByEmail(String email);
}
