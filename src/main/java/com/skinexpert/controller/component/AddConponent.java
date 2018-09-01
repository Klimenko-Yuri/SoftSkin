package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.entity.TypeComponent;
import com.skinexpert.service.ComponentService;
import com.skinexpert.util.Property;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
@WebServlet(urlPatterns = "/add-component")
public class AddConponent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ComponentService componentService = new ComponentService();

        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");
        //todo validation
        String addResult = null;

        Map<String, String> params = new HashMap<>();

        String[] body = req.getReader().lines().collect(Collectors.joining()).split("\"");

        for (int i = 1; i < body.length; i += 4) {
            if (body.length > i + 2)
            params.put(body[i], body[i + 2]);
            System.out.println(body[i] + " -> " + body[i+2]);
        }

        String name = params.get("name");
        String description = params.get("description");
        String type = params.get("type");
        String id = params.get("id");

        Component component = new Component();
        if (!id.equals("undefined")) {
            component.setId(Long.valueOf(id));
            addResult = "Компонент изменен";
        }

        System.out.println("name -> " + name + "\ndescription -> " + description);

        component.setName(name);
        component.setDescription(description);
        component.setType(TypeComponent.DANGER.getTypeByAtribute(type));
        component.setVisiable(true);

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

        String outMessage = new Gson().toJson(addResult);
        resp.getWriter().write(outMessage);

    }
}
