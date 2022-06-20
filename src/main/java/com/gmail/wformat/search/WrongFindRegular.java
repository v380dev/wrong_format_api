package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongFindRegular {

    //для однопотокового виконання
    public static List<WrongObj> getWrongObjList(List<String> allLines, List<String> allObjects, Vocabulary voc, List<Case> cases) {
        List<WrongObj> wrongObjs = new ArrayList<>();
        for (String obj : allObjects) {
            WrongObj wrongObj = getWrongObj(allLines, obj, voc, cases);
            if (wrongObj != null) {
                wrongObjs.add(wrongObj);
            }
        }
        return wrongObjs;
    }

    //для багатопотокового виконання
    public static WrongObj getWrongObj(List<String> allLines, String nameObj, Vocabulary voc, List<Case> cases) {
        WrongObj wo = new WrongObj(nameObj);
        for (Case caze : cases) {
            Pattern pattern = Pattern.compile(String.format(caze.getRegExp(), nameObj));
            List<Integer> indexes = voc.getMap().get(nameObj);
            for (Integer index : indexes) {
                String currentLine = allLines.get(index);
                if (currentLine.contains(caze.getPreFilter())) {
                    Matcher matcher = pattern.matcher(currentLine);
                    if (matcher.find()) {
                        wo.getNumberLines().add(index + 1);
                    }
                }
            }
        }
        return wo.getNumberLines().size() > 0 ? wo : null;
    }

}