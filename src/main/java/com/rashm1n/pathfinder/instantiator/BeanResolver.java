package com.rashm1n.pathfinder.instantiator;

import com.rashm1n.pathfinder.model.BeanDetails;

public interface BeanResolver {
    <T> BeanDetails resolveClass(Class<T> tClass);
}
