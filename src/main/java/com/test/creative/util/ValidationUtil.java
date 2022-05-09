package com.test.creative.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    public static final String VALID_EMAIL_ADDRESS_REGEX = "^(.+)@(.+)$";

    public static boolean emailIsValido(String email){
        final var pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);
        final var matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
