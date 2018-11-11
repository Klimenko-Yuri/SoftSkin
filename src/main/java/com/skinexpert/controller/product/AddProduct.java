package com.skinexpert.controller.product;

import com.google.gson.Gson;
import com.skinexpert.entity.Component;
import com.skinexpert.service.ComponentService;
import com.skinexpert.validation.AddComponentValidation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add component in database by response body(name, description, type)
 * or change it, if id is present.
 * Send JSON request like report string.
 */
@WebServlet(urlPatterns = "/add-product")
public class AddProduct extends HttpServlet {

    ComponentService componentService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
