package com.rashm1n.pathfinder;

import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.exceptions.InvalidAnnotationException;
import com.rashm1n.pathfinder.instantiator.InstanceMaker;
import com.rashm1n.pathfinder.instantiator.SingletonMaker;
import com.rashm1n.pathfinder.model.BeanDefinition;
import com.rashm1n.pathfinder.registry.InstanceHolder;
import com.rashm1n.pathfinder.registry.InstanceRegistry;
import com.rashm1n.pathfinder.scanner.BeanDefinitionResolver;
import com.rashm1n.pathfinder.scanner.ClassPathScanner;
import com.rashm1n.pathfinder.scanner.Resolver;
import com.rashm1n.pathfinder.scanner.Scanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AppCtx {
    private InstanceRegistry registry;
    private Set<BeanDefinition> beanDefinitions;
    private Resolver beanDefinitionResolver;

    public AppCtx() {
        registry = new InstanceRegistry();
        beanDefinitions = new HashSet<>();
        beanDefinitionResolver = new BeanDefinitionResolver();
    }

    public AppCtx(String basePackage) throws IOException {
        this();
        Scanner classScanner = new ClassPathScanner();
        classScanner.scan(basePackage);
        Map<String, Class<?>> classNameMap = classScanner.getClassNameList();
        classNameMap.forEach((name, clazz)-> {
            try {
                if (!clazz.isInterface()) {
                    BeanDefinition beanDefinition = beanDefinitionResolver.resolveClass(clazz);
                    Object instance;
                    if (clazz.isAnnotationPresent(Singleton.class)) {
                        instance = SingletonMaker.instantiate(clazz);
                    } else {
                        instance = InstanceMaker.instantiate(clazz);
                    }

                    registry.addInstance(name,new InstanceHolder<>(instance));
                    beanDefinition.setInstance(instance);
                    beanDefinitions.add(beanDefinition);
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     InvalidAnnotationException | NoSuchMethodException e) {
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

    public <T> T getBean(String name)
            throws InvalidAnnotationException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        Optional<BeanDefinition> optionalBeanDefinition =
                beanDefinitions.
                        stream().
                        filter(bd -> bd.getName().equals(name)).
                        findFirst();

        if (!optionalBeanDefinition.isPresent()) {
            throw new RuntimeException("Bean with the given name is not present");
        }

        BeanDefinition beanDefinition = optionalBeanDefinition.get();

        if (beanDefinition.isSingleton()) {
            return (T)SingletonMaker.instantiate(beanDefinition.getClazz());
        }

        return (T)InstanceMaker.instantiate(beanDefinition.getClazz());
    }


}
