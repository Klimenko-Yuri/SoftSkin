package com.skinexpert.validation;

/**
 * If return null -> validation OK
 */
public interface Valid {

    /**
     * @return description of trouble
     */
    String check(Object o);
}
