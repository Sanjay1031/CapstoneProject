package com.nashss.se.budgetme.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Util class for validation handling 
 */
public class ValidatorUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN =
            Pattern.compile("[^a-zA-Z\\s-'.]");

    private static final Pattern INVALID_NUMBER_PATTERN = Pattern.compile("^(0|[1-9]\\d*)(\\.\\d+)?$");

    private static final Pattern EMAIL_CHARACTER_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");


    /**
     * Static utility method to validate a String.
     * @param stringToValidate the String to check
     * @return a boolean representing the validity of the string
     */
    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }

    /**
     * Static utility method to validate an expenseAmount.
     * @param number the number to check
     * @return a boolean representing the validity of the string as a number
     */
    public static boolean isValidNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        } else {
            return INVALID_NUMBER_PATTERN.matcher(number).find();
        }
    }
    /**
     * Static utility method to validate a user.
     * @param email the email to check
     * @return a boolean representing the validity of the string as email
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        } else {
            return EMAIL_CHARACTER_PATTERN.matcher(email).find();
        }
    }

    /**
     * Static utility method to generate a random, unique employeeID.
     * @return a random 5 digit alphanumeric
     */
    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
