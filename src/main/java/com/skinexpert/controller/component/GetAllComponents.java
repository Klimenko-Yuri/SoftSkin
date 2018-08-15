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
import java.util.List;

/**
 * Created by Mihail Kolomiets on 15.08.18.
 */
@WebServlet(urlPatterns = "/get-all-component")
public class GetAllComponents extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        List<Component> componentList = new ComponentService().getAll();

        String outMessage = new Gson().toJson(componentList);
        resp.getWriter().write(outMessage);
    }
}
