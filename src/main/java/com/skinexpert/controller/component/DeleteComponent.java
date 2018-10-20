package com.skinexpert.controller.component;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Delete component by id from delete request
 */
@WebServlet(urlPatterns = "/delete-component/*")
public class DeleteComponent extends HttpServlet {
    private Logger logger;
    private ComponentService componentService;
    private String message = "Компонент %s %s";

    @Override
    public void init() {
        this.logger = LogManager.getLogger(this.getClass());
        this.componentService = new ComponentService();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("application/json; charset = utf8");
        try {
            req.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while setCharset process.", e);
            throw new RuntimeException(e);
        }

        String deleteItem = req.getPathInfo().substring(1);
        logger.info("Trying to remove component by its id");
        try {
            int id = Integer.parseInt(deleteItem);
            logger.info(String.format("id param for removing is: %d", id));
            Component component = componentService.deleteById(id);
            responseMessageForDeleteRequest(resp, component.getName(), " удален");
        } catch (NumberFormatException e) {
            logger.error("Error while parsing deleteItem", e);
        }

        logger.info("Trying to remove component by its name");
        Component component = componentService.findByName(deleteItem);
        if (component == null) {
            responseMessageForDeleteRequest(resp, "null", " не найден в базе");
        } else {
            component.setVisiable(false);
            componentService.addComponent(component);
            responseMessageForDeleteRequest(resp, component.getName(), " скрыт от поиска");
        }
    }

    private void responseMessageForDeleteRequest(HttpServletResponse resp, String componentName, String action) {
        String outMessage = new Gson().toJson(String.format(message, componentName, action));
        try {
            resp.getWriter().write(outMessage);
        } catch (IOException e) {
            logger.error("Error while creating response message.", e);
            throw new RuntimeException(e);
        }
    }
}
