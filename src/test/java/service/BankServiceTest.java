package service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.repository.InMemoryClientRepository;
import org.kaczucha.service.BankService;

import java.util.ArrayList;
import java.util.List;

public class BankServiceTest {
    private BankService service;
    private List<Client> clients;


    @BeforeEach
    public void setup() {
        service = new BankService(new InMemoryClientRepository(new ArrayList<>()));
        clients = new ArrayList<>();
        service = new BankService(new InMemoryClientRepository(clients));
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek", emailFrom, List.of(new Account(1000, "PL")));
        final Client clientTo = new Client("Bartek", emailTo, List.of(new Account(500, "PL")));
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 100;
        //when
        service.transfer(emailFrom, emailTo, amount);
        //then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedClientFrom = new Client("Alek", emailFrom, List.of(new Account(900, "PL")));
        final Client expectedClientTo = new Client("Bartek", emailTo, List.of(new Account(600, "PL")));

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
        final Client clientFrom = new Client("Alek", emailFrom, List.of(new Account(1000, "PL")));
        final Client clientTo = new Client("Bartek", emailTo, List.of(new Account(500, "PL")));
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 1000;
        // when
        service.transfer(emailFrom, emailTo, amount);
        // then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedClientFrom = new Client("Alek", emailFrom, List.of(new Account(0, "PL")));
        final Client expectedClientTo = new Client("Bartek", emailTo, List.of(new Account(1500, "PL")));

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
        final Client clientFrom = new Client("Alek", emailFrom, List.of(new Account(100, "PL")));
        final Client clientTo = new Client("Bartek", emailTo, List.of(new Account(100, "PL")));
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
        final Client clientFrom = new Client("Alek", emailFrom, List.of(new Account(100, "PL")));
        final Client clientTo = new Client("Bartek", emailTo, List.of(new Account(100, "PL")));
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
        final Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
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
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
        clients.add(client);
        //when
        service.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Alek", email, List.of(new Account(100, "PL")));
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_allBalance_balanceSetToZero() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
        clients.add(client);
        //when
        service.withdraw(email,100);
        //then
        Client expectedClient = new Client("Alek", email,List.of(new Account(100, "PL")));
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_negativeAmount_illegalArgumentException() {
        //given
        String email = "a@a.pl";
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
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
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
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
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
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
        Client client = new Client("Alek", "a@a.pl", List.of(new Account(100, "PL")));
        clients.add(client);
        //when
        service.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Alek", "a@a.pl", List.of(new Account(100, "PL")));
        Assertions.assertTrue(clients.contains(expectedClient));

    }

    @Test
    public void withdraw_nullEmail_IllegalArgumentException() {
        //given
        String email = null;
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
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
        Client client = new Client("Alek", email, List.of(new Account(100, "PL")));
        clients.add(client);
        //when
        service.withdraw(email, 50.5);
        //then
        Client expectedClient = new Client("Alek", email, List.of(new Account(100, "PL")));
        clients.add(expectedClient);
        Assertions.assertTrue(clients.contains(expectedClient));

    }
}
