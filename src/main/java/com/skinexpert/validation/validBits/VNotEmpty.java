package com.skinexpert.validation.validBits;

import com.skinexpert.validation.Valid;

/**
 * Created by Mihail Kolomiets on 8/20/18.
 */
public class VNotEmpty implements Valid {

    private String invalidCheck;

    public VNotEmpty(String invalidCheckMessage) {
        invalidCheck = invalidCheckMessage;
    }

    @Override
    public String check(Object o) {
        if(!(o instanceof String)) {
            return "its no string";
        }

        String validString = (String) o;

        if (validString.length() == 0) {
            return invalidCheck;
        }

        return null;
    }
}
