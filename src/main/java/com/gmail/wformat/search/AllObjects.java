package com.gmail.wformat.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllObjects {
    public static final String REG_OBJ = "(?<=#.*`)\\w+(?=`)";


    public static List<String> getList(List<String> listAllLine, int startPos, int quantThreads) {
        List<String> listAllObj = new ArrayList<>();
        Pattern patternStart = Pattern.compile(REG_OBJ);

        for (int i = startPos; i < listAllLine.size(); i+=quantThreads) {
            String currentLine = listAllLine.get(i);
            Matcher matcher = patternStart.matcher(currentLine);
            while (matcher.find()) {
                listAllObj.add(matcher.group());
            }
        }
        return listAllObj;
    }
}