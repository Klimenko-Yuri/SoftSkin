package com.skinexpert.validation;

import com.skinexpert.entity.Component;
import com.skinexpert.validation.validBits.VMostAny;
import com.skinexpert.validation.validBits.VNotEmpty;

import java.util.LinkedList;

public class AddComponentValidation {

    public String checkAllFields(String name) {

        String validationResult;

        LinkedList<Valid> validationBits = new LinkedList<>();
        Validator validator = new Validator(validationBits);
        validationBits.add(new VNotEmpty("Поле юзера пустое."));
        validationBits.add(new VMostAny("Имя не может быть более 30 символов", 30));
        validationResult = validator.doValidation(name);
        //validationBits.clear(); // for next

        return validationResult;
    }


}
