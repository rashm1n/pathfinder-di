package com.rashm1n.di.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceRegistry {
    private Map<String, InstanceHolder> instances;
    private Map<String, InstanceHolder> namedInstances;

    public InstanceRegistry() {
        this.instances = new ConcurrentHashMap<String, InstanceHolder>();
    }

    public void addInstance(String name, InstanceHolder holder) {
        instances.put(name, holder);
    }

    public InstanceHolder getInstanceHolder(String name) {
        return instances.get(name);
    }


    public void addNamedInstance(String name, InstanceHolder holder) {
        namedInstances.put(name, holder);
    }

    public InstanceHolder getNamedInstanceHolder(String name) {
        return namedInstances.get(name);
    }
}
