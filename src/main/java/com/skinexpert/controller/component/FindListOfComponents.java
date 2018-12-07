package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.service.ComponentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


@WebServlet(urlPatterns = "/get-list-of-component/*")
public class FindListOfComponents extends HttpServlet{

    private Logger logger = LogManager.getLogger();
    private static final ComponentService COMPONENT_SERVICE = ComponentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");
        try {
            req.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while setCharset process.", e);
            throw new RuntimeException(e);
        }

        List<String> requestString = Arrays.asList(req.getParameter("list").split(";"));

        String outMessage;
        if(requestString.isEmpty()){
            outMessage = "['error' : 'Format is incorrect']";
        } else {
            outMessage = new Gson().toJson(COMPONENT_SERVICE.getListOfComponents(requestString));
        }
        try {
            resp.getWriter().write(outMessage);
        } catch (IOException e) {
            logger.error("Error while creating response message.", e);
            throw new RuntimeException(e);
        }

    }
}
