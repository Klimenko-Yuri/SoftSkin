package com.skinexpert.validation.validBits;

import com.skinexpert.entity.Component;
import com.skinexpert.validation.AddComponentValidation;
import com.skinexpert.validation.Valid;

import javax.servlet.http.HttpServletRequest;

/**
 * Sample validation for Integer digit.
 * May usage in the js requests.
 */
public class VCheckDigit implements Valid {

    private String message;

    public VCheckDigit(String message) {
        this.message = message;
    }

    @Override
    public String check(Object o) {
        if (o.equals("undefined") || !(o instanceof Integer))
            return message;

        return null;
    }

    /**
     * Parse object -> string -> digit
     * Return integer if possible or 0
     */
    public static Integer getIntegerFromString(Object o) {
        if (o instanceof Integer)
            return (Integer) o;
        if (o instanceof String) {
            String number = (String) o;
            try {
                return Integer.valueOf(number);
            } catch (NumberFormatException n) {
                return 0;
            }
        }
        return 0;
    }

}
