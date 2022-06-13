package org.kaczucha.repository;

import org.apache.commons.lang3.StringUtils;
import org.kaczucha.repository.entity.Client;

import java.util.*;

public class InMemoryClientRepository implements ClientRepository {
    private List<Client> clients;

    public InMemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void save(Client client) {
        if (client.getEmail() == null) {
            throw new IllegalArgumentException("cannot add null email");
        }
        if (client.getBalance() < 0) {
            throw new IllegalArgumentException("Cannot add client with negative balance");
        }
        ////
//        if (client.equals(findByEmail(client.getEmail()))) {
//            throw new IllegalArgumentException("cannot add same client");
//        }
//        Client clientFromDb = findByEmail(client.getEmail());
//        if (client.getEmail().equals(clientFromDb.getEmail())) {
//            throw new IllegalArgumentException("cannot add same client with same @");
//        }
        if (existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("cannot add same client with same @");
        }
        client.setEmail(client.getEmail().toLowerCase());

        clients.add(client);

        /**
         * client.getEmail()  .equals(    findByEmail(email)     ) TO SAMO
         * STRING                            CLIENT
         *
         * findByEmail(...).equals(client.getEmail()) TO SAMO
         */
    }

    @Override
    public void deleteClient(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
        if (notExistsByEmail(email)) {
            throw new IllegalArgumentException("client with following email not found");
        }
        Optional<Client> client = findByEmail(email);
        if (client.isPresent() && client.get().getBalance() != 0) {
            throw new IllegalArgumentException("cannot remove client with balance !=0");
        }

        clients.remove(client.get());
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clients
                .stream()
                .filter(client -> Objects.equals(client.getEmail(), email.toLowerCase()))
                .findFirst();
//                .orElseThrow(() -> new IllegalArgumentException("client with following email " + email + " not found "));
    }

    @Override
    public boolean existsByEmail(String email) {
//        for (Client client: clients) {
//            if (StringUtils.equalsIgnoreCase(client.getEmail(), email)) {
//                return true;
//            }
//        }
//        return false;
        return clients.stream()
                .anyMatch(client -> StringUtils.equalsIgnoreCase(client.getEmail(), email));
    }

    @Override
    public boolean notExistsByEmail(String email) {
        return clients.stream()
                .noneMatch(client -> StringUtils.equalsIgnoreCase(client.getEmail(), email));
    }
}
