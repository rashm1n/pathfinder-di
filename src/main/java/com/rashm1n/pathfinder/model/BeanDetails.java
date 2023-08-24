package com.rashm1n.pathfinder.model;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanDetails<T> {
    private Class<T> clazz;
    private T instance;
    private Constructor<?> selectedConstructor;

    public BeanDetails(Class<T> clazz, T instance, Constructor<T> selectedConstructor) {
        this.clazz = clazz;
        this.instance = instance;
        this.selectedConstructor = selectedConstructor;
    }

    public BeanDetails() {
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Constructor<?> getSelectedConstructor() {
        return selectedConstructor;
    }

    public void setSelectedConstructor(Constructor<?> selectedConstructor) {
        this.selectedConstructor = selectedConstructor;
    }
}
