package com.irateam.sixhandshakes.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class RequestUtils {

    public static final int MAX_USER_COUNT = 25;

    private RequestUtils() {
        throw new AssertionError(getClass().getName() + " can't be instantiated");
    }

    public static List<String> buildRequestStrings(Collection<Integer> idsCollection) {
        List<Integer> idsList = new ArrayList<>(idsCollection);
        int iterationCount = (int) Math.ceil(idsList.size() / (double) MAX_USER_COUNT);
        List<String> result = new ArrayList<>(iterationCount);

        for (int multiplier = 0; multiplier < iterationCount; multiplier++) {
            StringBuilder builder = new StringBuilder();
            for (int i = MAX_USER_COUNT * multiplier; i < (MAX_USER_COUNT * (multiplier + 1)) && i < idsList.size(); i++) {
                builder.append(String.valueOf(idsList.get(i)));
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            result.add(builder.toString());
        }

        return result;
    }
}
