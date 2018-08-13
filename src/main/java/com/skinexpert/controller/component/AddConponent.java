package com.skinexpert.controller.component;

import com.skinexpert.entity.Component;
import com.skinexpert.entity.TypeComponent;
import com.skinexpert.service.ComponentService;
import com.skinexpert.util.Property;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
@WebServlet(urlPatterns = "/add-component")
public class AddConponent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ComponentService componentService = new ComponentService();

        req.setCharacterEncoding("utf8");
        //todo validation
        String addResult = null;
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("type");
        String id = req.getParameter("id");

        Component component = new Component();
        if(id != null) {
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
        } else if (component.getId() == 0){
            addResult = "Не удалось добавить компонент т.к: " + addResult;
        }

        req.getSession().setAttribute("message", addResult);
        resp.sendRedirect(Property.HOST + "/admin/add-component.html");
    }
}
