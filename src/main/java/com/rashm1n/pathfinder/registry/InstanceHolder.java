package com.rashm1n.pathfinder.registry;

public class InstanceHolder<T> {
    private T instance;

    public InstanceHolder(T instance) {
        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }
}
