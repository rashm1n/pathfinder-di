package com.rashm1n.pathfinder.scanner;

import com.rashm1n.pathfinder.annotations.Inject;
import com.rashm1n.pathfinder.annotations.Named;
import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.model.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinitionResolver implements Resolver{

    @Override
    public BeanDefinition resolveClass(Class<?> clazz) throws NoSuchMethodException {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClazz(clazz);
        beanDefinition.setCanonicalName(clazz.getCanonicalName());
        beanDefinition.setNamed(clazz.isAnnotationPresent(Named.class));
        beanDefinition.setName(extractNameFromAnnotation(clazz));
        beanDefinition.setChosenConstructor(choseConstructor(clazz));
        beanDefinition.setSingleton(clazz.isAnnotationPresent(Singleton.class));
//        beanDefinition.setParentClasses(getParentClasses(clazz)); // TODO
        return beanDefinition;
    }



    private Constructor<?> choseConstructor(Class<?> clazz) throws NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }
        return clazz.getDeclaredConstructor();
    }

    private String extractNameFromAnnotation(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Named.class)) {
            if (clazz.getAnnotation(Named.class).value().isEmpty()) {
                return clazz.getSimpleName();
            }
            return clazz.getAnnotation(Named.class).value();
        }

        return null;
    }
}
