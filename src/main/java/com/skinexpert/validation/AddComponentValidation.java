package com.skinexpert.validation;

import com.skinexpert.entity.Component;
import com.skinexpert.entity.TypeComponent;
import com.skinexpert.validation.validBits.VCheckDigit;
import com.skinexpert.validation.validBits.VMostAny;
import com.skinexpert.validation.validBits.VNotEmpty;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class AddComponentValidation {

    public String checkRequestData(HttpServletRequest req, Component component) {

        String validationResult;

        Map<String, String> params = new HashMap<>();

        String[] body;
        try {
            body = req.getReader().lines().collect(Collectors.joining()).split("\"");
        } catch (IOException e) {
            return e.getMessage();
        }

        for (int i = 1; i < body.length; i += 4) {
            if (body.length > i + 2)
                params.put(body[i], body[i + 2]);
            System.out.println(body[i] + " -> " + body[i + 2]);
        }

        String name = params.get("name");
        String description = params.get("description");
        String type = params.get("type");
        String id = params.get("id");

        LinkedList<Valid> validationBits = new LinkedList<>();
        validationBits.add(new VNotEmpty("Поле юзера пустое."));
        validationBits.add(new VMostAny("Имя не может быть более 30 символов", 30));
        Validator validator = new Validator(validationBits);
        validationResult = validator.doValidation(name);

        component.setId(VCheckDigit.getIntegerFromString(id));
        component.setName(name);
        component.setDescription(description);
        component.setType(TypeComponent.getTypeByAtribute(type));

        return validationResult;
    }


}
