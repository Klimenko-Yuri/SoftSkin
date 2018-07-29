package com.skinexpert.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test rest controller. will be deleted any time...
 */
@RestController
@RequestMapping(value = "/rest")
public class TestController {

    @RequestMapping(value = "/any", method = RequestMethod.GET)
    public String anyStringPage() {
        return "test ok";
    }
}
