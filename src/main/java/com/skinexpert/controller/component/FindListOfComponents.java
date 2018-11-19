package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = "/get-list-of-component/*")
public class FindListOfComponents extends HttpServlet{

    private Logger logger;
    private static final ComponentService COMPONENT_SERVICE = ComponentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");
        req.setCharacterEncoding("utf8");

        List<String> requestString = Arrays.asList(req.getParameter("list").split(";"));

        String outMessage;
        if(requestString.size()==0){
            outMessage = "['error' : 'Format is incorrect']";
        } else {
            outMessage = new Gson().toJson(COMPONENT_SERVICE.getListOfComponents(requestString));
        }
        resp.getWriter().write(outMessage);

    }
}
