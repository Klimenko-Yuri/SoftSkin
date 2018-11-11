package com.skinexpert.entity;

/**
 * Represent type of components
 */
public enum TypeComponent {
    DANGER("D"),
    MIDDLE("M"),
    GOOD("G");

    private String attribute;

    TypeComponent(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public static TypeComponent getTypeByAttribute(String type) {
        if (type == null) {
            return MIDDLE;
        }
        type = type.toUpperCase();
        for (TypeComponent typeComponent : TypeComponent.values()) {
            if (typeComponent.getAttribute().equals(type))
                return typeComponent;
        }
        return MIDDLE;
    }
}
