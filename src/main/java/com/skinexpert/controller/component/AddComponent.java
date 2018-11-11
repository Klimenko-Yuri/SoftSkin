package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.controller.component.validation.AddComponentValidation;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Add component in database by response body(name, description, type)
 * or change it, if id is present.
 * Send JSON request like report string.
 */
@WebServlet(urlPatterns = "/add-component")
public class AddComponent extends HttpServlet {
    private Logger logger;
    private static final ComponentService COMPONENT_SERVICE = ComponentService.getInstance();

    @Override
    public void init() {
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json; charset = utf8");
        try {
            req.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while setCharset process.", e);
            throw new RuntimeException(e);
        }

        Component component = new Component();
        String addResult = new AddComponentValidation().checkRequestData(req, component);

        component.setVisiable(true);

        if (addResult == null) {
            addResult = addComponent(component);
        }

        String outMessage = new Gson().toJson(addResult);
        try {
            resp.getWriter().write(outMessage);
        } catch (IOException e) {
            logger.error("Error while creating response message.", e);
            throw new RuntimeException(e);
        }
    }

    private String addComponent(Component component) {
        StringBuilder action = new StringBuilder("Компонент ");
        action.append(component.getName());

        if (component.getId() == 0 && COMPONENT_SERVICE.findByName(component.getName()) != null) {
            action.append(" уже есть в базе");
            return action.toString();
        } else {
            COMPONENT_SERVICE.saveOrUpdate(component);
            if (component.getId() == 0) {
                action.append(" добавлен в базу");
                return action.toString();
            }
        }
        action.append(" изменен");
        return action.toString();
    }
}
