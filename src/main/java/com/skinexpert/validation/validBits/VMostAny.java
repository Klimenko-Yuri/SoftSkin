package com.skinexpert.validation.validBits;

import com.skinexpert.validation.Valid;

/**
 * Created by Mihail Kolomiets on 8/20/18.
 */
public class VMostAny implements Valid{

    private String invalidCheck;
    private int howMuch;

    public VMostAny(String invalidCheckMassage, int howMuch) {
        invalidCheck = invalidCheckMassage;
        this.howMuch = howMuch;
    }

    @Override
    public String check(Object o) {
        if(!(o instanceof String)) {
            return "its no string";
        }
        if (((String) o).length() > howMuch) {
            return invalidCheck;
        }

        return null;
    }
}
