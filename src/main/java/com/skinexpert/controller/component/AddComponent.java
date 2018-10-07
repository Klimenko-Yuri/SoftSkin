package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import com.skinexpert.validation.AddComponentValidation;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add component in database by response body(name, description, type)
 * or change it, if id is present.
 * Send JSON request like report string.
 */
@WebServlet(urlPatterns = "/add-component")
public class AddComponent extends HttpServlet {

    ComponentService componentService = new ComponentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        Component component = new Component();
        String addResult = new AddComponentValidation().checkRequestData(req, component);

        component.setVisiable(true);

        if (addResult == null)
            addResult = addComponent(component);

        String outMessage = new Gson().toJson(addResult);
        resp.getWriter().write(outMessage);
    }

    private String addComponent(Component component) {
        String action = "Компонент " + component.getName();
        try {
            if (component.getId() == 0 && componentService.findByName(component.getName()) != null) {
                return action + " уже есть в базе";
            } else {
                componentService.addComponent(component);
                if (component.getId() == 0)
                    return action + " добавлен в базу";
            }

            //for debug
        } catch (Exception e) {
            return e.getMessage();
        }
        return action + " изменен";
    }

}
