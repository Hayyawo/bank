package org.kaczucha.repository;

import org.kaczucha.Client;

import java.util.Optional;

public interface ClientRepository  {
    void save(Client client);

    void deleteClient(String email);

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean notExistsByEmail(String email);
}
