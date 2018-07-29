package com.skinexpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Server start point
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
