package org.kaczucha.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class InMemoryClientRepositoryTest {
    private InMemoryClientRepository repository;
    private ArrayList<Client> clients;

//    Praca domowa:
//    korzystając z TDD zaimplementuj metodę deleteClient(String email) która:
//            - usuwa klienta z repozytorium
//- rzuca wyjątek w przypadku nieprawidłowego maila
//- nie pozwala na null maila
//- nie pozwala na usunięcie klienta jeżeli balance != 0

    @BeforeEach
    public void setup() {
        clients = new ArrayList<>();
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


    @Test
    public void deleteClientIsDeletingCorrectly() {
        //given
        final String email = "a@a.pl";
        Client expectedClient = new Client("alek", email, 100);
        //when
        repository.save(expectedClient);
        //then
        Client actualClient = repository.findByEmail("a@a.pl");

        assertEquals(expectedClient, actualClient);

    }

    @Test
    public void deleteClients_invalidEmail(){
        String email = "invalid@email";
        Client client = new Client("alek",email,100);

        Assertions.assertThrows(IllegalArgumentException.class,()->repository.deleteClient(email));
    }

    @Test
    public void deleteClients_nullMail_throwIllegalArgumentException(){
        String mail = null;
        Client client = new Client("alek",mail,100);

        Assertions.assertThrows(IllegalArgumentException.class,()-> repository.deleteClient(mail));
    }

    @Test
    public void deleteClients_balanceEqualZero_throwIllegalArgumentException(){
        double balance = 100;
        String email = "a@a.pl";
        Client client = new Client("alek",email,balance);

        Assertions.assertThrows(IllegalArgumentException.class,()-> repository.deleteClient(email));
    }
}