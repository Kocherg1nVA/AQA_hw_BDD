package ru.netology.data;

import lombok.Value;
import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode{
        String code;
    }

    @Value
    public static class CardInfo{
        String number;
    }

    public static UserInfo getUserInfo(){
        return new UserInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode(UserInfo userInfo){
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCard(){
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard(){
        return new CardInfo("5559 0000 0000 0002");
    }

    public static String generateAmount(int balance){
        int amount = new Random().nextInt(balance);
        return Integer.toString(amount);
    }

}
