package com.rashm1n.pathfinder.instantiator;

import com.rashm1n.pathfinder.annotations.Inject;
import com.rashm1n.pathfinder.annotations.Named;
import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.exceptions.InvalidAnnotationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class InstanceMaker {
    public static <T> T instantiate(Class<T> clazz)
            throws InvocationTargetException, InstantiationException, IllegalAccessException,
            InvalidAnnotationException {
        Constructor<?> c = clazz.getDeclaredConstructors()[0];
        T instance = (T) c.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                if (type.isAnnotationPresent(Singleton.class)) {
                    Object fieldInstance = SingletonMaker.instantiate(type);
                    field.set(instance, fieldInstance);
                    continue;
                }
                Object fieldInstance = instantiate(type);
                field.set(instance,fieldInstance);
            }
        }
        return instance;
    }
}
