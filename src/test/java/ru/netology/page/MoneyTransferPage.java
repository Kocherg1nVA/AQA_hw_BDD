package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.getFirstCard;
import static ru.netology.data.DataHelper.getSecondCard;

public class MoneyTransferPage {

    private final SelenideElement heading = $("h1.heading");
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement cardFromField = $("[data-test-id='from'] input");
    private final SelenideElement cardToField = $("[data-test-id='to'].input");
    private final SelenideElement addButton = $("[data-test-id='action-transfer'].button");

    public MoneyTransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage moneyTransfer(String amount){
        amountField.setValue(amount);
        cardFromField.setValue(selectCardFrom());
        addButton.click();
        return new DashboardPage();
    }

    public String selectCardFrom(){
        String[] cards = new String[] {getFirstCard().getNumber(), getSecondCard().getNumber()};
        String cardTo = cardToField.getText();
        String cardFrom = null;
        for (String card : cards) {
            if (!cardTo.equals(card)) {
                cardFrom = card;
            }
        }
        return cardFrom;
    }



}
