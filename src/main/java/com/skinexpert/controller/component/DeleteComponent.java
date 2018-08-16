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

        String message = "";
        Component component = null;

        try {
            int id = Integer.valueOf(req.getPathInfo().substring(1));
            System.out.println(id);
            component = new ComponentService().deleteById(id);
        } catch (NumberFormatException e) {
            message = "Incorrect value of id";
        }

        if (component != null) {
            message = "Компонент " + component.getName() + " удален";
        }

        String outMessage = new Gson().toJson(message);
        resp.getWriter().write(outMessage);

    }
}
