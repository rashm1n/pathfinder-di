package com.rashm1n.pathfinder;

import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.exceptions.InvalidAnnotationException;
import com.rashm1n.pathfinder.instantiator.EagerInstantiator;
import com.rashm1n.pathfinder.instantiator.InstanceMaker;
import com.rashm1n.pathfinder.instantiator.SingletonMaker;
import com.rashm1n.pathfinder.registry.InstanceHolder;
import com.rashm1n.pathfinder.registry.InstanceRegistry;
import com.rashm1n.pathfinder.registry.SingletonRegistry;
import com.rashm1n.pathfinder.scanner.ClassScanner;
import com.rashm1n.pathfinder.scanner.Scanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AppCtx {
    private InstanceRegistry registry;
    private SingletonRegistry singletonRegistry;

    public AppCtx() {
        registry = new InstanceRegistry();
        singletonRegistry = new SingletonRegistry();
    }

    public AppCtx(String basePackage) throws IOException {
        this();
        Scanner classScanner = new ClassScanner();
        classScanner.scan(basePackage);
        Map<String, Class<?>> classNameMap = classScanner.getClassNameList();
        classNameMap.forEach((name, clazz)-> {
            try {
                if (!clazz.isInterface()) {
                    if (clazz.isAnnotationPresent(Singleton.class)) {
                        registry.addInstance(name,new InstanceHolder<>(SingletonMaker.instantiate(clazz)));
                    } else {
                        registry.addInstance(name,new InstanceHolder<>(InstanceMaker.instantiate(clazz)));
                    }
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     InvalidAnnotationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> T getBean(Class<T> clazz) {
        return (T)doGetBean(clazz.getName()).getInstance();
    }

    private InstanceHolder doGetBean(String name) {
        return registry.getInstanceHolder(name);
    }


}
