package org.kaczucha.repository;

import org.kaczucha.Client;

import java.util.Objects;
import java.util.Set;

public class InMemoryClientRepository implements ClientRepository
{
private Set<Client> clients;
    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clients
                .stream()
                .filter(client -> Objects.equals(client.getEmail(),email))
                .findFirst()
                .get();
    }

    public InMemoryClientRepository(Set<Client> clients) {
        this.clients = clients;
    }

}
