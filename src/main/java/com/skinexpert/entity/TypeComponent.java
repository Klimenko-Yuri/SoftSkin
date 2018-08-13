package com.skinexpert.entity;

/**
 * Created by Mihail Kolomiets on 10.08.18.
 */
public enum TypeComponent {
    DANGER("D"),
    MIDDLE("M"),
    GOOD("G");

    private String atribute;

    TypeComponent(String atribute) {
        this.atribute = atribute;
    }

    public String getAtribute() {
        return atribute;
    }

    public TypeComponent getTypeByAtribute(String type) {
        type = type.toUpperCase();
        for(TypeComponent typeComponent : TypeComponent.values()) {
            if(typeComponent.getAtribute().equals(type))
                return typeComponent;
        }
        return MIDDLE;
    }
}
