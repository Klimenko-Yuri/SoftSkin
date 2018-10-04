package com.skinexpert.entity;

import javax.persistence.*;

@Entity
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idProduct;

    @Column
    private String name;

    @Column
    private String description;

    public long getId() {
        return idProduct;
    }

    public void setId(long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
