package com.skinexpert.service;

import com.skinexpert.dao.ComponentDao;
import com.skinexpert.dao.impl.HibernateComponentDaoImpl;
import com.skinexpert.entity.Component;

import java.util.List;

public class ComponentService {
    private ComponentDao componentDao;

    private ComponentService() {
        this.componentDao = new HibernateComponentDaoImpl();
    }

    private static class ComponentServiceHolder {
        private static final ComponentService INSTANCE = new ComponentService();
    }

    public static ComponentService getInstance() {
        return ComponentServiceHolder.INSTANCE;
    }

    public Component saveOrUpdate(Component component) {
        return componentDao.saveOrUpdate(component);
    }

    public Component deleteById(long id) {
        return componentDao.deleteById(id);
    }

    public Component findById(long id) {
        return componentDao.findById(id);
    }

    public Component findByName(String name) {
        return componentDao.findByName(name);
    }

    public List<Component> getAll() {
        return componentDao.getAll();
    }

    public List<Component> findNameBySubstring(String search) {
        return componentDao.findNameBySubstring(search);
    }
}
