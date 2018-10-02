package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import com.skinexpert.util.Property;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete component by id from delete request
 */
@WebServlet(urlPatterns = "/delete-component/*")
public class DeleteComponent extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        String message;
        Component component;
        ComponentService service = new ComponentService();
        String deleteItem =req.getPathInfo().substring(1);

        try {
            int id = Integer.valueOf(deleteItem);
            System.out.println(id);
            component = service.deleteById(id);
            message = "Компонент " + component.getName() + " удален";
        } catch (NumberFormatException e) {
            component = service.findByName(deleteItem);
            component.setVisiable(false);
            service.addComponent(component);
            message = "Компонент " + component.getName() + " скрыт от поиска";
        }

        String outMessage = new Gson().toJson(message);
        resp.getWriter().write(outMessage);

    }
}
