package store.global.validator;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.regex.Pattern;

public class InputValidator {

    private static final String PRODUCT_FORMAT_REGEX = "^\\[([^-]+-\\d+)](,\\[([^-]+-\\d+)])*$";
    private static final String YES_NO_REGEX = "^[YN]$";

    public static void validateProductInput(String input) {
        validateEmpty(input);
        validateProductFormat(input);
    }

    private static void validateEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw ConvenienceStoreException.from(ErrorMessage.INVALID_INPUT_FORMAT);
        }
    }

    private static void validateProductFormat(String input) {
        if (!Pattern.matches(PRODUCT_FORMAT_REGEX, input)) {
            throw ConvenienceStoreException.from(ErrorMessage.INVALID_INPUT_FORMAT);
        }
    }

    public static void validateYesNoInput(String input) {
        validateEmpty(input);
        if (!Pattern.matches(YES_NO_REGEX, input)) {
            throw ConvenienceStoreException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }
}
