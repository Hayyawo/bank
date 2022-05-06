package org.kaczucha.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

public class InMemoryClientRepositoryTest {
    private InMemoryClientRepository repository;
    private Set<Client> clients;

    @BeforeEach
    public void setup() {
        clients = new HashSet<>();
        repository = new InMemoryClientRepository(clients);
    }

    @Test
    void verifyIfUserIsAddingCorrectly() {
        //Given
        Client client = new Client("alan", "alan@pl", 100);
        Client expectedClient = new Client("alan", "alan@pl", 100);
        //when
        repository.save(client);
        Client actualClient = clients.stream().findFirst().get();

        //then
//        assert actualClient.equals(expectedClient);
        assertEquals(expectedClient, actualClient);
    }


}
