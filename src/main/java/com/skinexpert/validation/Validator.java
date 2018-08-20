package com.skinexpert.validation;

import java.util.List;

/**
 * Created by Mihail Kolomiets on 8/20/18.
 */
public class Validator {

    private List<Valid> validList;
    private Object o;

    public Validator(List validList) {
        this.validList = validList;
    }

    public void addToValidation(Valid v) {
        validList.add(v);
    }

    public String doValidation() {

        String result;

        for (Valid v : validList) {
            result = v.check(o);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}
