package com.skinexpert.controller;

import com.google.gson.Gson;
import com.skinexpert.util.Property;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Send gson massage from be to fe
 */
@WebServlet(urlPatterns = "/get-back-attr")
public class MessageBridge extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset = utf8");
        HttpSession session = req.getSession();

        String message = "";

        try{
            Object o = session.getAttribute("message");
            if (o != null)
            message = session.getAttribute("message").toString();
        } catch (Exception e) {

        }

        String outMessage = new Gson().toJson(message);
        resp.getWriter().write(outMessage);
    }
}
