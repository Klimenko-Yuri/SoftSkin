package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mihail Kolomiets on 10.08.18.
 */
@WebServlet(urlPatterns = "/find-component/*")
public class FindComponent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        String name = req.getPathInfo().substring(1);
        Component component = null;
        if (name.length() > 0) {
            try {
                component = new ComponentService().findByName(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String outMessage = new Gson().toJson(component);
        resp.getWriter().write(outMessage);
    }
}
