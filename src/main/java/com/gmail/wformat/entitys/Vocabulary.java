package com.gmail.wformat.entitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {
    Map<String, List<Integer>> map;
    private final String regular = "\\b\\w{3,}\\b";
    private final Pattern pattern = Pattern.compile(regular);

    public Map<String, List<Integer>> getMap() {
        return map;
    }

    public void fill(List<String> allLines) {
        map = new HashMap<>();
        for (int i = 0; i < allLines.size(); i++) {
            Matcher matcher = pattern.matcher(allLines.get(i));
            while (matcher.find()) {
                String key = matcher.group();
                List<Integer> list = map.getOrDefault(key, new ArrayList<>());
                list.add(i);
                map.put(key, list);
            }
        }
    }

}






