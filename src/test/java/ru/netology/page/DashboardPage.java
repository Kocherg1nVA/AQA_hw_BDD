package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private static final SelenideElement heading = $("h1.heading");
    private static final ElementsCollection cards = $$(".list__item");
    private static final SelenideElement firstCardAddButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");
    private static final SelenideElement secondCardAddButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(String cardNumber) {
        val text = cards.find(Condition.text(cardNumber.substring(15, 19))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage addToFirstCard() {
        firstCardAddButton.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage addToSecondCard() {
        secondCardAddButton.click();
        return new MoneyTransferPage();
    }
}
