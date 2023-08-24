package com.rashm1n.pathfinder.instantiator;

import com.rashm1n.pathfinder.annotations.Inject;
import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.exceptions.InvalidAnnotationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SingletonMaker {
    private static Map<Class<?>, Object> singletonMap = new HashMap<>();

    public static Object instantiate(Class<?> clazz)
            throws InvalidAnnotationException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        if (singletonMap.keySet().contains(clazz)) {
            return singletonMap.get(clazz);
        }

        if (!clazz.isAnnotationPresent(Singleton.class)) {
            throw new InvalidAnnotationException();
        }

        Constructor<?> constructor = clazz.getConstructors()[0];
        Object instance = constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }

            Class<?> type = field.getType();

            if (type.isAnnotationPresent(Singleton.class)) {
                Object fieldInstance = instantiate(type);
                field.set(instance, fieldInstance);
            }

            InstanceMaker instanceMaker = new InstanceMaker();
            Object fieldInstance = instanceMaker.instantiate(type);
            field.set(instance, fieldInstance);

        }

        return singletonMap.put(clazz, instance);
    }
}
