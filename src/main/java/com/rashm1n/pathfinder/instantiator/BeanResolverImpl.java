package com.rashm1n.pathfinder.instantiator;

import com.rashm1n.pathfinder.annotations.Inject;
import com.rashm1n.pathfinder.model.BeanDetails;
import java.lang.reflect.Constructor;

public class BeanResolverImpl implements BeanResolver{
    @Override
    public <T> BeanDetails resolveClass(Class<T> aClass) {
        BeanDetails<T> beanDetails = new BeanDetails<>();
        beanDetails.setClazz(aClass);
        beanDetails.setSelectedConstructor(selectConstructor(aClass));
        return beanDetails;
    }

    private Constructor<?> selectConstructor(Class<?> aClass) {
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }
        return constructors[0];
    }
}
