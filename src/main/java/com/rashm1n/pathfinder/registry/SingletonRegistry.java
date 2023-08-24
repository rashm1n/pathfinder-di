package com.rashm1n.pathfinder.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingletonRegistry implements Registry{
    private Map<Class<?>, Object> singletonMap = new HashMap<>();
    public void addSingleton(Class<?> clazz, Object object) {
        singletonMap.put(clazz, object);
    }
    public Object getSingleton(Class<?> clazz) {
        return singletonMap.getOrDefault(clazz, null);
    }
}
