package com.skinexpert.DAO;

import com.skinexpert.entity.Component;
import com.skinexpert.entity.Product;

import java.util.List;

public class DBProductDao implements ProductDAO{

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void deleteProduct(Integer id) {

    }

    @Override
    public void addComponentToProduct(Integer id, Component component) {

    }

    @Override
    public void deleteComponentFromProduct(Integer id) {

    }

    @Override
    public List<Product> findProductsByComponents(List<Component> componentList) {
        return null;
    }

    @Override
    public List<Product> findByItem(Product product) {
        return null;
    }
}
