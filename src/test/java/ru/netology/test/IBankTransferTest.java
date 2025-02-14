package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.MoneyTransferPage;

import static ru.netology.data.DataHelper.*;
import static ru.netology.page.LoginPage.validLogin;
import static ru.netology.page.VerificationPage.validVerify;

public class IBankTransferTest {

    String amount = generateAmount(5000);

    @BeforeEach
    public void setUp(){
//        Configuration.browser = "firefox";
        Selenide.open("http://localhost:9999");
        var userInfo = getUserInfo();
        validLogin(userInfo);
        validVerify(getVerificationCode(userInfo));
    }

    @Test
    public void shouldSuccessfulTransferFromFirsToSecond(){
        var dashboardPage = new DashboardPage();
        var moneyTransferPage = new MoneyTransferPage();
        int initialFirstCardBalance = DashboardPage.getCardBalance(getFirstCard().getNumber());
        int initialSecondCardBalance = DashboardPage.getCardBalance(getSecondCard().getNumber());
        dashboardPage.addToFirstCard();
        moneyTransferPage.moneyTransfer(amount);

        int expectedFirstCardBalance = initialFirstCardBalance + Integer.parseInt(amount);
        int expectedSecondCardBalance = initialSecondCardBalance - Integer.parseInt(amount);

        Assertions.assertEquals(expectedFirstCardBalance, DashboardPage.getCardBalance(getFirstCard().getNumber()));
        Assertions.assertEquals(expectedSecondCardBalance, DashboardPage.getCardBalance(getSecondCard().getNumber()));
    }
}
