package org.kaczucha.repository;

import org.kaczucha.Client;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class InMemoryClientRepository implements ClientRepository {
    private List<Client> clients;

    @Override
    public void save(Client client) {
        if (client.getEmail() == null) {
            throw new NullPointerException("cannot add null email");
        }
        if (client.getBalance() < 0) {
            throw new IllegalArgumentException("Cannot add client with negative balance");
        }
        if (client.equals(findByEmail(client.getEmail()))) {
            throw new IllegalArgumentException("cannot add same client");
        }
        if (client.getEmail().equals(findByEmail(client.getEmail()))) {
            throw new IllegalArgumentException("cannot add same client with same @");

        }

        client.setEmail(client.getEmail().toLowerCase());
        clients.add(client);
    }

    public void deleteClient(String email) {
        if (email == null){
            throw new IllegalArgumentException("email cannot be null");
        }
        Client client = findByEmail(email);
        if(!clients.contains(client)){
            throw new IllegalArgumentException("client with following email not found");
        }
        if (client.getBalance() != 0){
            throw new IllegalArgumentException("cannot remove client with balance !=0");
        }

         clients.remove(client);

    }

    @Override
    public Client findByEmail(String email) {
        return clients
                .stream()
                .filter(client -> Objects.equals(client.getEmail(), email.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("client with following email " + email + " not found "));
    }

    public InMemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

}
