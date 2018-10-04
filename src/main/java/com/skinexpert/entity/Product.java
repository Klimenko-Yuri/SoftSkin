package com.skinexpert.entity;

import javax.persistence.*;
import java.util.List;

@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Component> componentList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProduct")
    private TypeProduct typeProduct;

    public Product() {
    }
}
