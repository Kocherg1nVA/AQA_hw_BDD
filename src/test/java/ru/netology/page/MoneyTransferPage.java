package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.getFirstCard;
import static ru.netology.data.DataHelper.getSecondCard;

public class MoneyTransferPage {

    private static final SelenideElement heading = $("h1.heading");
    private static final SelenideElement amountField = $("[data-test-id='amount'] input");
    private static final SelenideElement cardFromField = $("[data-test-id='from'] input");
    private static final SelenideElement addButton = $("[data-test-id='action-transfer'].button");
    private static final SelenideElement disabledField = $("[data-test-id='to'] input.input__control");
    private static final SelenideElement errMsg = $("[data-test-id='error-notification'] _notification__content");

    public MoneyTransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public static DashboardPage moneyTransfer(String amount) {
        amountField.setValue(amount);
        cardFromField.setValue(selectCardFrom());
        addButton.click();
        return new DashboardPage();
    }

    public static String selectCardFrom() {
        String[] cards = new String[]{getFirstCard().getNumber(), getSecondCard().getNumber()};
        String cardTo = disabledField.getValue().substring(15, 19);
        String cardFrom = null;
        for (String card : cards) {
            if (!cardTo.equals(card.substring(15, 19))) {
                cardFrom = card;
            }
        }
        return cardFrom;
    }

    public void amountMoreThanBalance() {
        errMsg.shouldHave(Condition.exactText("Ошибка! Недостаточно средств на счёте.")).shouldBe(Condition.visible);
    }
}
