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

    public Component() {
    }

    public Component(String name, String description, TypeComponent type, boolean visiable) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.visiable = visiable;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) id;
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (description == null ? 0 : description.hashCode());
        hash = 31 * hash + (type == null ? 0 : type.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Component component = (Component) obj;
        return id == component.id
                && (name.equals(component.name)
                && description.equals(component.description)
                && type.getAttribute().equals(component.type.getAttribute()));
    }
}
