package com.skinexpert.controller.component.validation.validBits;

import com.skinexpert.controller.component.validation.Valid;

/**
 * Check string on length
 */
public class VMostAny implements Valid {

    /**
     * Description
     */
    private String invalidCheck;
    private int length;

    public VMostAny(String invalidCheckMassage, int length) {
        invalidCheck = invalidCheckMassage;
        this.length = length;
    }

    @Override
    public String check(Object o) {
        if (!(o instanceof String)) {
            return "its no string";
        }
        if (((String) o).length() > length) {
            return invalidCheck;
        }

        return null;
    }
}
