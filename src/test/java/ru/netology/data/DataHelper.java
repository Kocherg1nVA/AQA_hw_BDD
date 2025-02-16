package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {

    public DataHelper() {
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardInfo {
        String number;
    }

    public static UserInfo getUserInfo() {
        return new UserInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode(UserInfo userInfo) {
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }

    public static String generateAmount(int balance) {
        return Integer.toString(new Random().nextInt(balance) + 1);
    }

    public static String generateInvalidAmount(int balance) {
        return Integer.toString(Math.abs(balance) + new Random().nextInt(10_000));
    }
}
