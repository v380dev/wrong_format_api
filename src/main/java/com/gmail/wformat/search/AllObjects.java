package com.gmail.wformat.search;

import com.gmail.wformat.util.RegExpressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllObjects {

    public static List<String> getList(List<String> listAllLine) {
        List<String> listAllObj = new ArrayList<>();
        Pattern patternStart = Pattern.compile(RegExpressions.REG_START_OBJ);
//        Pattern patternStart = Pattern.compile(RegExp.START_OBJ.get.toString());
        Pattern patternWhole = Pattern.compile(RegExpressions.REG_WHOLE_OBJ);
//        Pattern patternWhole = Pattern.compile(RegExp.WHOLE_OBJ.get.toString());

        for (String currentLine : listAllLine) {
            Matcher matcherStart = patternStart.matcher(currentLine);
            Matcher matcherWhole = patternWhole.matcher(currentLine);
            if (matcherWhole.find()) {
                matcherStart.find();
                listAllObj.add(currentLine.substring(matcherStart.end(), matcherWhole.end()-1));
            }
        }

        return listAllObj;
    }


}
