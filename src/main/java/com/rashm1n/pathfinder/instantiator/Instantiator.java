package com.rashm1n.pathfinder.instantiator;

public interface Instantiator <T> {
    T doInstantiate(Class<T> tClass);
}
