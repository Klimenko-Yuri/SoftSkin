package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.entity.TypeComponent;
import com.skinexpert.service.ComponentService;
import com.skinexpert.util.Property;
import com.skinexpert.validation.Valid;
import com.skinexpert.validation.Validator;
import com.skinexpert.validation.validBits.VMostAny;
import com.skinexpert.validation.validBits.VNotEmpty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
@WebServlet(urlPatterns = "/add-component")
public class AddConponent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        String addResult;
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("type");
        String id = req.getParameter("id");

        //init validation
        Validator validator = new Validator(new LinkedList<>());

        //prepare need validation function
        validator.addToValidation(new VNotEmpty("Поле юзера пустое."));
        validator.addToValidation(new VMostAny("Имя не может быть более 30 символов", 30));

        //do validation
        addResult = validator.doValidation();
        if (addResult == null) {

            ComponentService componentService = new ComponentService();
            Component component = new Component();

            if (id != null) {
                component.setId(Long.valueOf(id));
                addResult = "Компонент изменен";
            }
            component.setName(name);
            component.setDescription(description);
            component.setType(TypeComponent.DANGER.getTypeByAtribute(type));

            try {
                if (component.getId() == 0 && componentService.findByName(name) != null) {
                    addResult = "Компонент " + name + " уже есть в базе";
                } else {
                    component = componentService.addComponent(component);
                }
            } catch (Exception e) {
                addResult = e.toString();
            }
            if (component.getId() != 0 && addResult == null) {
                addResult = "Компонент " + component.getName() + " добавлен в базу";
            } else if (component.getId() == 0) {
                addResult = "Не удалось добавить компонент т.к: " + addResult;
            }
        }

        String outMessage = new Gson().toJson(addResult);
        resp.getWriter().write(outMessage);
    }
}
