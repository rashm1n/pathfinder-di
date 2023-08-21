package com.rashm1n.di.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceRegistry {
    private Map<String, InstanceHolder> instances;

    public InstanceRegistry() {
        this.instances = new ConcurrentHashMap<String, InstanceHolder>();
    }

    public void addInstance(String name, InstanceHolder holder) {
        instances.put(name, holder);
    }

    public InstanceHolder getInstanceHolder(String name) {
        return instances.get(name);
    }
}
