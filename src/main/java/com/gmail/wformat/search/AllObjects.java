package com.gmail.wformat.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllObjects {
    public static final String REG_START_OBJ = "#{1,} `";
    public static final String REG_WHOLE_OBJ = "#{1,}[ *\\t]`\\w+`";


    public static List<String> getList(List<String> listAllLine) {
        List<String> listAllObj = new ArrayList<>();
        Pattern patternStart = Pattern.compile(REG_START_OBJ);
        Pattern patternWhole = Pattern.compile(REG_WHOLE_OBJ);

        for (int i = 0; i < listAllLine.size(); i++) {
            String currentLine = listAllLine.get(i);
            Matcher matcherStart = patternStart.matcher(currentLine);
            Matcher matcherWhole = patternWhole.matcher(currentLine);
            boolean isStart = matcherStart.find();
            boolean isWhole = matcherWhole.find();
            if (i > 10000) {
            }
            if (isWhole) {
                listAllObj.add(currentLine.substring(matcherStart.end(), matcherWhole.end() - 1));
            }
        }
        return listAllObj;
    }
}