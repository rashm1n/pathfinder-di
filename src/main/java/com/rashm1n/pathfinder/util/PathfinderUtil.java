package com.rashm1n.pathfinder.util;

import java.util.Arrays;

public class PathfinderUtil {
    public static String concatenate(String ... strings) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(strings).forEach(builder::append);
        return builder.toString();
    }
}
