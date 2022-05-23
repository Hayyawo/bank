package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.service.BankService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankServiceTest2 {

    /*
    Napiszcie testy jednostkowe + poprawcie implementacje dla metod klasy BankService.
Specyfikacja metod:
save(Client client) ma:
 - zapisywać klienta w repozytorium
 - nie pozwalać na zapisanie 2 razy tego samego klienta
 - nie pozwalać na zapisanie 2 klientów z tym samym adresem email
 - zapisywać adres email w repozytorium zawsze z małych liter
 - nie pozwalać na zapisanie klienta z null name/null mail/negative balance
findByEmail(String email) ma:
 - wyszukiwać klienta po adresie email (wielkość liter nie ma mieć znaczenia)
 - rzucać wyjątek jeżeli danego klienta nie ma w repozytorium
transfer(String mailFrom, String mailTo, amount) ma:
 - przesyłać liczbę pieniędzy określoną w parametrze amount z konta klienta from na konto klienta to
 - wielkość liter w adresach email nie powinna mieć znaczenia
 - nie pozwalać na stworzenie się debetu
 - nie pozwalać na wysłanie ujemnej kwoty
 - nie pozwalać na wysłanie pieniędzy do samego siebie
 - weryfikować istnienie klienta from i to
     */

    private BankService service;

    //todo zamockuj repo i usuń tą listę, ciulu


    @BeforeEach
    public void setup() {
        service = mock(BankService.class);
    }

    @Test
    public void save_savingAlreadyExistingClient_throw() {
        String email = "a@a.pl";
        Client client = new Client("jan", email, 100);
        when(service.findByEmail(email)).thenReturn(client);


        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
    }

    @Test
    public void save_savingAlreadyExistingEmail_throw() {
        Client firstClient = new Client("alek", "a@a.pl", 100);
        Client secondClient = new Client("marek", "a@a.pl", 2500);
        service.save(firstClient);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.save(secondClient));

    }
//    //  - zapisywać adres email w repozytorium zawsze z małych liter
//
//    @Test
//    public void save_clientWithUppercaseEmailOk_clientSaved() {
//        //given
//        String email = "UPPER@EMAIL.PL";
//        Client client = new Client("alek", email, 100);
//        //when
//        clients.add(client);
//        service.save(client);
//        //then
//        Client actualClient = service.findByEmail(email);
//        Client expectedClient = new Client("alek", "upper@email.pl", 100);
//
//        clients.add(expectedClient);
//        service.save(expectedClient);
//        assertEquals(client, expectedClient);
//    }
//
//    /**
//     * nie pozwalać na zapisanie klienta z null name
//     */
//    @Test
//    public void save_clientWithNullEmail_throwNullPointerException() {
//        //given
//        String email = null;
//        Client client = new Client("alek", email, 100);
//        //when
//        //then
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
//    }
//
//    /**
//     * nie pozwalać na zapisanie klienta z negative balance
//     */
//    @Test
//    public void save_clientWithNegativeBalance_throwIllegalArgumentException() {
//        //given
//        double balance = -100;
//        Client client = new Client("alek", "a@a.pl", balance);
//        //when
//        //then
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
//    }
//
//
//    /**
//     * wyszukiwać klienta po adresie email (wielkość liter nie ma mieć znaczenia)
//     */
//
//    @Test
//    public void findByEmail_uppercaseInputEmailOk_clientFound() {
//        //given
//        Client expectedClient = new Client("alek", "a@a.pl", 100);
//        clients.add(expectedClient);
//        //when
//        Client actualClient = service.findByEmail("A@A.PL");
//        //then
//        assertEquals(expectedClient, actualClient);
//
//    }
//
//    /**
//     * rzucać wyjątek jeżeli danego klienta nie ma w repozytorium
//     */
//
//    @Test
//    public void findByEmail_nonExistingEmailInput_throwsNoSuchElementException() {
//        //given/when/then
//        Assertions.assertThrows(IllegalArgumentException.class, () ->
//            service.findByEmail("invalid_email@wp.pl"));
//
//    }
}
