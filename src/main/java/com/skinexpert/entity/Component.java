package com.skinexpert.entity;

import javax.persistence.*;

/**
 * Created by Mihail Kolomiets on 09.08.18.
 */
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private TypeComponent type;

    @Column(nullable = false)
    private boolean visiable;

    public TypeComponent getType() {
        return type;
    }

    public void setType(TypeComponent type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isVisiable() {
        return visiable;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }
}
