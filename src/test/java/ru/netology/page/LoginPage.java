package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static final SelenideElement loginField = $("[data-test-id='login'] input");
    private static final SelenideElement passwordField = $("[data-test-id='password'] input");
    private static final SelenideElement actionLoginButton = $("[data-test-id='action-login']");

    public static VerificationPage validLogin(DataHelper.UserInfo userInfo){
        loginField.setValue(userInfo.getLogin());
        passwordField.setValue(userInfo.getPassword());
        actionLoginButton.click();
        return new VerificationPage();
    }
}
