package com.rashm1n.pathfinder.instantiator;

import com.rashm1n.pathfinder.annotations.Inject;
import com.rashm1n.pathfinder.annotations.Named;
import com.rashm1n.pathfinder.annotations.Singleton;
import com.rashm1n.pathfinder.model.BeanDetails;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

// TODO - Refactor
public class EagerInstantiator {
    public static <T> T instantiate(Class<? extends T> clazz, Map<String, Class<?>> classMap)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException {
        Constructor<?> constructor = clazz.getConstructor();
        T instance = (T)constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        processFields(classMap, instance, fields);
        return instance;
    }

    public static <T> T instantiateV2(BeanDetails<T> beanDetails, Map<String, Class<?>> classMap, Map<Class<?>, Object> singletonMap)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException {
        Constructor<?> constructor = beanDetails.getSelectedConstructor();

        if (beanDetails.getClazz().isAnnotationPresent(Singleton.class)) {
            if (singletonMap.containsKey(beanDetails.getClazz())) {
                return (T)singletonMap.get(beanDetails.getClazz());
            }

            T instance = (T)constructor.newInstance();
            instance = processFields(
                    classMap,
                    instance,
                    beanDetails.getClazz().getDeclaredFields());
            singletonMap.put(beanDetails.getClazz(), instance);
        }

        T instance = (T)constructor.newInstance();
        Field[] fields = beanDetails.getClazz().getDeclaredFields();
        processFields(classMap, instance, fields);
        return instance;
    }

    private static <T> T processFields(Map<String, Class<?>> classMap, T instance, Field[] fields)
            throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException,
            NoSuchMethodException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();

                if (type.isInterface()) {
                    Map<String, Class<?>> subclasses = findSubClasses(type, classMap);
                    if (subclasses.size() == 0) {
                        throw new RuntimeException("No implementations found");
                    } else {
                        Class<?> subclass = null;
                        String fieldName = field.getName();

                        for (String key : subclasses.keySet()) {
                            Class<?> clazz1 = subclasses.get(key);
                            if (clazz1.isAnnotationPresent(Named.class)) {
                                if (clazz1.getAnnotation(Named.class).value().equals(fieldName)) {
                                    subclass = Class.forName(clazz1.getName());
                                }
                            }
                        }

                        if (subclass == null) {
                            throw new RuntimeException("No implementation found");
                        }

                        Object fieldInstance = instantiate(subclass, classMap);
                        field.set(instance, fieldInstance);

                    }

                } else {
                    Object fieldInstance = instantiate(type, classMap);
                    field.set(instance,fieldInstance);
                }
            }
        }

        return instance;
    }

    private static Map<String, Class<?>> findSubClasses(Class<?> interfaze, Map<String, Class<?>> classMap) {
        Map<String, Class<?>> subclassMap = new HashMap<>();
        classMap.forEach((name,clazz) -> {
            if (!clazz.isInterface()) {
                if (clazz.getInterfaces().length > 0) {
                    if (clazz.getInterfaces()[0] == interfaze) {
                        subclassMap.put(name, clazz);
                    }
                }
            }
        });
        return subclassMap;
    }
}
