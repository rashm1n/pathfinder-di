package com.rashm1n.pathfinder.model;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanDefinition {
    private Class<?> clazz;
    private String canonicalName;
    private boolean isNamed;
    private String name;
    private boolean isSingleton;
    private Constructor<?> chosenConstructor;
    private Object instance;
    private Set<Class<?>> parentClasses;

    public BeanDefinition(Class<?> clazz, String canonicalName, boolean isNamed, String name,
            Constructor<?> chosenConstructor, Object instance, Set<Class<?>> parentClasses) {
        this.clazz = clazz;
        this.canonicalName = canonicalName;
        this.isNamed = isNamed;
        this.name = name;
        this.chosenConstructor = chosenConstructor;
        this.instance = instance;
        this.parentClasses = parentClasses;
    }

    public BeanDefinition() {

    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public boolean isNamed() {
        return isNamed;
    }

    public void setNamed(boolean named) {
        isNamed = named;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constructor<?> getChosenConstructor() {
        return chosenConstructor;
    }

    public void setChosenConstructor(Constructor<?> chosenConstructor) {
        this.chosenConstructor = chosenConstructor;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Set<Class<?>> getParentClasses() {
        return parentClasses;
    }

    public void setParentClasses(Set<Class<?>> parentClasses) {
        this.parentClasses = parentClasses;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }
}
