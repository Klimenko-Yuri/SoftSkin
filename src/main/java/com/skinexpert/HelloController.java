package com.skinexpert;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping(value = "/jsp", method = RequestMethod.GET)
    public String helloPage() {
        return "hello";
    }

}