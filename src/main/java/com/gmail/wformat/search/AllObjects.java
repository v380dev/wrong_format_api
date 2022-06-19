package com.gmail.wformat.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllObjects {
    public static final String REG_OBJ = "(?<=#.*`)\\w+(?=`)";
    private static Pattern pattern = Pattern.compile(REG_OBJ);

    public static List<String> getList(List<String> listAllLine, int startPos, int quantThreads) {
        List<String> listAllObj = new ArrayList<>();
        for (int i = startPos; i < listAllLine.size(); i += quantThreads) {
            String currentLine = listAllLine.get(i);
            Matcher matcher = pattern.matcher(currentLine);
            while (matcher.find()) {
                listAllObj.add(matcher.group());
            }
        }
        return listAllObj;
    }

    public static List<String> getListFromOneLine(String currentLine) {
        List<String> listAllObj = new ArrayList<>();
        Matcher matcher = pattern.matcher(currentLine);
        while (matcher.find()) {
            listAllObj.add(matcher.group());
        }
        return listAllObj;
    }
}