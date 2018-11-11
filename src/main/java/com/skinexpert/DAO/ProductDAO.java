package com.skinexpert.DAO;

import com.skinexpert.entity.Component;
import com.skinexpert.entity.Product;

import java.util.List;

public interface ProductDAO {

    void addProduct(Product product);

    List<Product> getAllProducts();

    void deleteProduct(Integer id);

    void addComponentToProduct(Integer id, Component component);

    void deleteComponentFromProduct(Integer id);

    List<Product> findProductsByComponents(List<Component> componentList);

    List<Product> findByItem(Product product);
}
