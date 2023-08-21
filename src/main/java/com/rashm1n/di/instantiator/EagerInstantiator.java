package com.rashm1n.di.instantiator;

import com.rashm1n.di.annotations.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class EagerInstantiator {
    public static <T> T instantiate(Class<? extends T> clazz)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getConstructor();
        T instance = (T)constructor.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
//                System.out.println(field.getType());
                Class<?> type = field.getType();
                Object fieldInstance = instantiate(type);
                field.set(instance,fieldInstance);
            }
        }
        return instance;
    }
}
