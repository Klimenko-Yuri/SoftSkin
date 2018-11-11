package com.skinexpert.dao;

import com.skinexpert.entity.Component;

import java.util.List;

public interface ComponentDao {
    Component saveOrUpdate(Component component);

    Component deleteById(long id);

    Component findById(long id);

    Component findByName(String name);

    List<Component> getAll();

    List<Component> findNameBySubstring(String search);
}
