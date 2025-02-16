package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private static final SelenideElement heading = $(".paragraph");
    private static final SelenideElement codeField = $("[data-test-id='code'] input");
    private static final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
