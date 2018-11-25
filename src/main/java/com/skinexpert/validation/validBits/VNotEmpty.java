package com.skinexpert.validation.validBits;

import com.skinexpert.validation.Valid;

/**
 * Check String by context
 */
public class VNotEmpty implements Valid {

    /**
     * Description of trouble
     */
    private String invalidCheck;

    public VNotEmpty(String invalidCheckMessage) {
        invalidCheck = invalidCheckMessage;
    }

    @Override
    public String check(Object o) {
        if (!(o instanceof String)) {
            return "its no string";
        }

        String validString = (String) o;

        if (validString.length() == 0) {
            return invalidCheck;
        }

        return null;
    }
}
