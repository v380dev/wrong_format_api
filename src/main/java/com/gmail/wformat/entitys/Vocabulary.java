package com.gmail.wformat.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {
    Map<String, List<Integer>> map;
    private final String regular = "\\b\\w{4,}\\b";
    private final Pattern pattern = Pattern.compile(regular);

    public void fill(String inputLine, int indexInpLine) {
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find()) {
            String key = matcher.group();
            List<Integer> list = map.getOrDefault(key, new ArrayList<>());
            list.add(indexInpLine);
            map.put(key, list);
        }
    }

    public List<Integer> getIndexList (String nameObject) {
        return map.get(nameObject);
    }

}






