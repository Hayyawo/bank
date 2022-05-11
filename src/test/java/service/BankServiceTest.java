package service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaczucha.Client;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.InMemoryClientRepository;
import org.kaczucha.service.BankService;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private ClientRepository clientRepository;
    private BankService service;

    //
    private List<Client> clients;
    //


    @BeforeEach
    public void setup() {
        service = new BankService(clientRepository);
        clients = new ArrayList<>();
        service = new BankService(new InMemoryClientRepository(clients));
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //given
        Mockito.when(clientRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek", emailFrom, 1000);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 100;
        //when
        service.transfer(emailFrom, emailTo, amount);
        //then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedClientFrom = new Client("Alek", emailFrom, 900);
        final Client expectedClientTo = new Client("Bartek", emailTo, 600);

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(expectedClientFrom)
                .isEqualTo(actualFromClient);
        softAssertions
                .assertThat(expectedClientTo)
                .isEqualTo(actualToClient);
        softAssertions.assertAll();
    }

    @Test
    public void transfer_allFounds_fundsTransferred() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek", emailFrom, 1000);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 1000;
        // when
        service.transfer(emailFrom, emailTo, amount);
        // then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedClientFrom = new Client("Alek", emailFrom, 0);
        final Client expectedClientTo = new Client("Bartek", emailTo, 1500);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(expectedClientFrom)
                .isEqualTo(actualFromClient);
        softAssertions
                .assertThat(expectedClientTo)
                .isEqualTo(actualToClient);
        softAssertions.assertAll();
    }

    @Test
    public void transfer_notEnoughFunds_thrownNoSufficientFundsException() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek", emailFrom, 100);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 1000;
        // when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }

    @Test
    public void transfer_negativeAmount_thrownIllegalArgumentException() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek", emailFrom, 100);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = -1000;
        // when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }



    @Test
    public void transfer_toSameClient_thrownException() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        // when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(email, email, 10)
        );

    }

    @Test
    public void withdraw_correctAmount_balanceChangedCorrectly() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        service.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Alek", email, 50);
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_allBalance_balanceSetToZero() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        service.withdraw(email,100);
        //then
        Client expectedClient = new Client("Alek", email,0);
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_negativeAmount_illegalArgumentException() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        int amount = -200;
        //then

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount));


    }

    @Test
    public void withdraw_zerAmount_illegalArgumentException() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        int amount = 0;
        //then

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount));


    }

    @Test
    public void withdraw_amountBiggerThanBalance_illegalArgumentException() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        int amount = 200;
        //then

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount));


    }

    @Test
    public void withdraw_incorrectEmail_illegalArgumentException() {
        //given
        String email = "incorrect_email@a.pl";
        int amount = 200;
        //when

        //then

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount));


    }

    @Test
    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
        //given
        String email = "A@A.PL";
        Client client = new Client("Alek", "a@a.pl", 100);
        clients.add(client);
        //when
        service.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Alek", "a@a.pl", 50);
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_nullEmail_IllegalArgumentException() {
        //given
        String email = null;
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when

        //then

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, 50));
    }

    @Test
    public void withdraw_floatNumber_balanceChangedCorrectly() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, 100);
        clients.add(client);
        //when
        service.withdraw(email, 50.5);
        //then
        Client expectedClient = new Client("Alek", email, 49.5);
        clients.add(expectedClient);
        Assertions.assertTrue(clients.contains(expectedClient));

    }
}
