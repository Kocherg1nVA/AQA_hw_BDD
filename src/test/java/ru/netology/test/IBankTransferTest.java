package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

import static ru.netology.data.DataHelper.*;

public class IBankTransferTest {

    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static MoneyTransferPage moneyTransferPage;
    int initialFirstCardBalance;
    int initialSecondCardBalance;


    @BeforeEach
    public void setUp() {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var userInfo = getUserInfo();
        verificationPage = loginPage.validLogin(userInfo);
        dashboardPage = verificationPage.validVerify(getVerificationCode(userInfo));
        initialFirstCardBalance = dashboardPage.getCardBalance(getFirstCard().getNumber());
        initialSecondCardBalance = dashboardPage.getCardBalance(getSecondCard().getNumber());
    }

    @Test
    public void shouldTransferFromFirsToSecond() {
        String amount = DataHelper.generateAmount(1_000);
        int expectedFirstCardBalance = initialFirstCardBalance + Integer.parseInt(amount);
        int expectedSecondCardBalance = initialSecondCardBalance - Integer.parseInt(amount);

        moneyTransferPage = dashboardPage.addToFirstCard();
        moneyTransferPage.moneyTransfer(amount, getSecondCard());

        Assertions.assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(getFirstCard().getNumber()));
        Assertions.assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(getSecondCard().getNumber()));
    }

    @Test
    public void shouldTransferFromSecondToFirst() {
        String amount = DataHelper.generateAmount(2_000);
        int expectedSecondCardBalance = initialSecondCardBalance + Integer.parseInt(amount);
        int expectedFirstCardBalance = initialFirstCardBalance - Integer.parseInt(amount);

        moneyTransferPage = dashboardPage.addToSecondCard();
        moneyTransferPage.moneyTransfer(amount, getFirstCard());

        Assertions.assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(getSecondCard().getNumber()));
        Assertions.assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(getFirstCard().getNumber()));
    }

    @Test
    public void shouldNotTransferAmountMoreThanBalance() {
        String amount = DataHelper.generateInvalidAmount(22_000);
        moneyTransferPage = dashboardPage.addToFirstCard();
        moneyTransferPage.moneyTransfer(amount, getFirstCard());
        moneyTransferPage.amountMoreThanBalance();

        Assertions.assertEquals(initialFirstCardBalance, dashboardPage.getCardBalance(getFirstCard().getNumber()));
        Assertions.assertEquals(initialSecondCardBalance, dashboardPage.getCardBalance(getSecondCard().getNumber()));
    }
}
