package com.rashm1n.di.scanner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ClassScanner implements Scanner {
    private Map<String, Class<?>> classNameList = new HashMap<>();

    public void scan(String basePath) throws IOException {
        doScan(basePath);
    }
    private void doScan(String basePath) throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = cl.getResources(basePath.replace('.', '/'));
        while (resources.hasMoreElements()) {
            String path = resources.nextElement().getPath();
            File filePath = new File(path);
            Arrays.stream(filePath.listFiles()).forEach(i -> {
                if (i.isDirectory()) {
                    try {
                        doScan(basePath + '.' + i.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    String name = basePath + '.' + i.getName().replace(".class", "");
                    try {
                        classNameList.put(name,Class.forName(name));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public Map<String, Class<?>> getClassNameList() {
        return classNameList;
    }

    public void setClassNameList(Map<String, Class<?>> classNameList) {
        this.classNameList = classNameList;
    }
}
