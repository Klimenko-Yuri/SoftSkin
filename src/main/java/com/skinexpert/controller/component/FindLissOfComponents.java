package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = "/get-list-of-component")
public class FindLissOfComponents extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");
        ComponentService service = new  ComponentService();

        List<String> requestString = req.getReader().lines().collect(Collectors.toList());

        if(requestString==null){
            resp.getWriter().write("Format is incorrect");;
        }

        if(requestString.size()==0){
            resp.getWriter().write(outMessage);
        }
        String outMessage = new Gson().toJson(service.getListOfComponents(requestString));
        resp.getWriter().write(outMessage);
    }
}
