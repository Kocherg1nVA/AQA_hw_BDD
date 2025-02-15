package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

import static ru.netology.data.DataHelper.*;
import static ru.netology.page.DashboardPage.addToFirstCard;
import static ru.netology.page.DashboardPage.addToSecondCard;
import static ru.netology.page.LoginPage.validLogin;
import static ru.netology.page.VerificationPage.validVerify;

public class IBankTransferTest {

    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static MoneyTransferPage moneyTransferPage;

    String amount = generateAmount(5000);
    String invalidAmount = generateInvalidAmount();

    @BeforeEach
    public void setUp() {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var userInfo = getUserInfo();
        verificationPage = validLogin(userInfo);
        dashboardPage = validVerify(getVerificationCode(userInfo));
    }

    @Test
    public void shouldTransferFromFirsToSecond() {
        int initialFirstCardBalance = DashboardPage.getCardBalance(getFirstCard().getNumber());
        int initialSecondCardBalance = DashboardPage.getCardBalance(getSecondCard().getNumber());

        moneyTransferPage = addToFirstCard();
        moneyTransferPage.moneyTransfer(amount);

        int expectedFirstCardBalance = initialFirstCardBalance + Integer.parseInt(amount);
        int expectedSecondCardBalance = initialSecondCardBalance - Integer.parseInt(amount);

        Assertions.assertEquals(expectedFirstCardBalance, DashboardPage.getCardBalance(getFirstCard().getNumber()));
        Assertions.assertEquals(expectedSecondCardBalance, DashboardPage.getCardBalance(getSecondCard().getNumber()));
    }

    @Test
    public void shouldTransferFromSecondToFirst() {
        int initialFirstCardBalance = DashboardPage.getCardBalance(getFirstCard().getNumber());
        int initialSecondCardBalance = DashboardPage.getCardBalance(getSecondCard().getNumber());

        moneyTransferPage = addToSecondCard();
        moneyTransferPage.moneyTransfer(amount);

        int expectedSecondCardBalance = initialSecondCardBalance + Integer.parseInt(amount);
        int expectedFirstCardBalance = initialFirstCardBalance - Integer.parseInt(amount);

        Assertions.assertEquals(expectedSecondCardBalance, DashboardPage.getCardBalance(getSecondCard().getNumber()));
        Assertions.assertEquals(expectedFirstCardBalance, DashboardPage.getCardBalance(getFirstCard().getNumber()));
    }

    @Test
    public void shouldNotTransferAmountMoreThanBalance() {
        int initialFirstCardBalance = DashboardPage.getCardBalance(getFirstCard().getNumber());
        int initialSecondCardBalance = DashboardPage.getCardBalance(getSecondCard().getNumber());

        moneyTransferPage = addToFirstCard();
        moneyTransferPage.moneyTransfer(invalidAmount);
        moneyTransferPage.amountMoreThanBalance();

        Assertions.assertEquals(initialFirstCardBalance, DashboardPage.getCardBalance(getFirstCard().getNumber()));
        Assertions.assertEquals(initialSecondCardBalance, DashboardPage.getCardBalance(getSecondCard().getNumber()));
    }
}
