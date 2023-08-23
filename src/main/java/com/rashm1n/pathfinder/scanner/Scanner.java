package com.rashm1n.pathfinder.scanner;

import java.io.IOException;
import java.util.Map;

public interface Scanner {
    void scan(String basePath) throws IOException;
    Map<String, Class<?>> getClassNameList();
    void setClassNameList(Map<String, Class<?>> classNameList);
}
