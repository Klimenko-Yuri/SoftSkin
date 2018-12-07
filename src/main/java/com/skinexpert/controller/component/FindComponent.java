package com.skinexpert.controller.component;

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
import java.util.Collections;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 10.08.18.
 */
@WebServlet(urlPatterns = "/find-component/*")
public class FindComponent extends HttpServlet {
    private Logger logger;
    private static final ComponentService COMPONENT_SERVICE = ComponentService.getInstance();

    @Override
    public void init() {
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("application/json; charset = utf8");
        try {
            req.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while setCharset process.", e);
            throw new RuntimeException(e);
        }

        String name = req.getPathInfo().substring(1);
        logger.info("Trying to findById components list");
        List<Component> components = (name.length() > 0 ?
                COMPONENT_SERVICE.findNameBySubstring(name) : Collections.emptyList());

        String outMessage = new Gson().toJson(components);
        try {
            resp.getWriter().write(outMessage);
        } catch (IOException e) {
            logger.error("Error while creating response message.", e);
            throw new RuntimeException(e);
        }
    }
}
