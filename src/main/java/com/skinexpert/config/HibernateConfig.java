package com.skinexpert.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConfig {
    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("SoftSkin");
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
