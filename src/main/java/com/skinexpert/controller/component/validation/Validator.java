package com.skinexpert.controller.component.validation;

import java.util.List;

/**
 * .
 */
public class Validator {

    private List<Valid> validList;

    public Validator(List validList) {
        this.validList = validList;
    }

    public void addToValidation(Valid v) {
        validList.add(v);
    }

    public String doValidation(Object o) {

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
