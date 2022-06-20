package com.gmail.wformat.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {
    private Map<String, List<Integer>> map;
    private static final String regular = "\\b\\w{3,}\\b";
    private static final Pattern pattern = Pattern.compile(regular);

    public Vocabulary() {
        map = new HashMap<>();
    }

    public Map<String, List<Integer>> getMap() {
        return map;
    }

    //для однопотокового виконання
    public void fillFromAllLines(List<String> allLines) {
        for (int i = 0; i < allLines.size(); i++) {
            fillFromOneLine(allLines.get(i), i);
        }
    }

    public void fillFromOneLine(String currentLine, int currentInd) {
        Matcher matcher = pattern.matcher(currentLine);
        while (matcher.find()) {
            String key = matcher.group();
            List<Integer> list = map.getOrDefault(key, new ArrayList<>());
            list.add(currentInd);
            map.put(key, list);
        }
    }

}






