package com.rashm1n.di;

import com.rashm1n.di.instantiator.EagerInstantiator;
import com.rashm1n.di.registry.InstanceHolder;
import com.rashm1n.di.registry.InstanceRegistry;
import com.rashm1n.di.scanner.ClassScanner;
import com.rashm1n.di.scanner.Scanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AppCtx {
    private InstanceRegistry registry;

    public AppCtx() {
        registry = new InstanceRegistry();
    }

    public AppCtx(String basePackage) throws IOException {
        this();
        Scanner classScanner = new ClassScanner();
        classScanner.scan(basePackage);
        Map<String, Class<?>> classNameMap = classScanner.getClassNameList();
        classNameMap.forEach((name, clazz)-> {
            try {
                registry.addInstance(name,new InstanceHolder<>(EagerInstantiator.instantiate(clazz)));
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
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
