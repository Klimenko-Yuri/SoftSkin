package com.skinexpert.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConfig {
    private static EntityManagerFactory factory;
    private static Logger logger;

    static {
        logger = LogManager.getLogger();
        try {
            factory = Persistence.createEntityManagerFactory("SoftSkin");
        } catch (Exception e) {
            logger.error("Exception during creation connection to db", e);
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
