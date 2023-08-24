package com.rashm1n.pathfinder.scanner;

import com.rashm1n.pathfinder.model.BeanDefinition;

public interface Resolver {
    BeanDefinition resolveClass(Class<?> clazz) throws NoSuchMethodException;
}
