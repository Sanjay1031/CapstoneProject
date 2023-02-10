package com.nashss.se.budgetme.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN =
            Pattern.compile("[^a-zA-Z\\s-'.]");

    private static final Pattern INVALID_NUMBER_PATTERN = Pattern.compile("^(0|[1-9]\\d*)(\\.\\d+)?$");


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
     * Static utility method to generate a random, unique employeeID.
     * @return a random 5 digit alphanumeric
     */
    public static String generateExpenseId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
