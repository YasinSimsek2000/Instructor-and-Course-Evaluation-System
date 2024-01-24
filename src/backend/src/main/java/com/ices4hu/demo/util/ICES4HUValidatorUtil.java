package com.ices4hu.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICES4HUValidatorUtil {
    public static String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

//    public static boolean TCKNIsValid(String tckn) {
//
//        if (tckn.charAt(0) == '0') return false;
//        if (tckn.length() != 11) return false;
//
//        if (!isNumeric(tckn)) return false;
//
//        int[] integerTCKN = new int[11];
//        int total = 0, oddDigitsTotal = 0, evenDigitsTotal = 0;
//        for (int i = 0; i < 11; i++) {
//            int digit = Integer.parseInt(String.valueOf(tckn.charAt(i)));
//            integerTCKN[i] = digit;
//            total += digit;
//            if (i % 2 == 0) oddDigitsTotal += digit;
//            else evenDigitsTotal += digit;
//        }
//
//        if (!(integerTCKN[9] == ((((oddDigitsTotal - integerTCKN[10]) * 7) - (evenDigitsTotal - integerTCKN[9])) % 10)))
//            return false;
//
//        return integerTCKN[10] == ((total - integerTCKN[10]) % 10);
//    }

    public static boolean emailIsValid(String email) {
        if (email == null) return true;
        Pattern pattern = Pattern.compile(ICES4HUValidatorUtil.emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

//    public static boolean phoneNumberIsValid(String telNo) {
//        if (telNo.length() != 10) return false;
//        return isNumeric(telNo);
//    }

//    public static boolean isNumeric(String str) {
//        for (int i = 0; i < str.length(); i++) {
//            if (!Character.isDigit(str.charAt(i))) return false;
//        }
//
//        return true;
//    }

    public static PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String generateRandomPassword(){
        return generateRandomPassword(PASSWORD_LENGTH, CHARACTERS);
    }

    public static String generateRandomPassword(int PASSWORD_LENGTH, String CHARACTERS){
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }


    public static Date now() {
        return ICES4HUValidatorUtil.now(0);
    }

    public static Date now(long addTime) {
        return new Date(System.currentTimeMillis() + addTime);
    }
}
