package com.gmail.wformat.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {
    Map<String, List<Integer>> map;
    private final String regular;
    private final Pattern pattern;

    public Vocabulary() {
        map = new HashMap<>();
        this.regular = "\\b\\w{3,}\\b";
        this.pattern = Pattern.compile(regular);
    }

    public Map<String, List<Integer>> getMap() {
        return map;
    }

    //для однопотокового виконання
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

    //для багатопотокового виконання
    public void fill(String currentLine, int currentInd) {
        Matcher matcher = pattern.matcher(currentLine);
        while (matcher.find()) {
            String key = matcher.group();
            List<Integer> list = map.getOrDefault(key, new ArrayList<>());
            list.add(currentInd);
            map.put(key, list);
        }
    }

}






