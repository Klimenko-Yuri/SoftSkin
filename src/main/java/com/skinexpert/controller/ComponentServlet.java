package com.skinexpert.controller;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/component")
public class ComponentServlet extends HttpServlet {
    private static final String EMPTY_STRING = "";
    private static final ComponentService COMPONENT_SERVICE = ComponentService.getInstance();
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while setCharset process.", e);
            throw new RuntimeException(e);
        }

        String componentId = Optional.ofNullable(req.getParameter("componentId")).orElse(EMPTY_STRING);
        String componentName = Optional.ofNullable(req.getParameter("name")).orElse(EMPTY_STRING);
        String miltyComponentsName = Optional.ofNullable(req.getParameter("list")).orElse(EMPTY_STRING);
        boolean isAllParametersEmpty = componentId.isEmpty() && componentName.isEmpty() && miltyComponentsName.isEmpty();
        String outMessage = EMPTY_STRING;

        if (isAllParametersEmpty) {
            outMessage = new Gson().toJson(COMPONENT_SERVICE.getAll());
        } else if (!componentId.isEmpty()) {
            try {
                Long id = Long.parseLong(componentId);
                Component byId = COMPONENT_SERVICE.findById(id);
                outMessage = new Gson().toJson(byId);
            } catch (NumberFormatException e) {
                logger.error("Exception while id parsing", e);
                throw new IllegalArgumentException(e);
            }
        } else if (!componentName.isEmpty()) {
            List<Component> componentList = COMPONENT_SERVICE.findNameBySubstring(componentName);
            outMessage = new Gson().toJson(componentList);
        } else if (!miltyComponentsName.isEmpty()) {
            List<String> requestString = Arrays.asList(miltyComponentsName.split(";"));
            outMessage = new Gson().toJson(COMPONENT_SERVICE.getListOfComponents(requestString));
        }

        try {
            resp.setContentType("application/json; charset = utf8");
            resp.getWriter().write(outMessage);
        } catch (IOException e) {
            logger.error("Error while creating response message.", e);
            throw new RuntimeException(e);
        }
    }
}
