package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongFindRegular {

    //для однопотокового виконання
    public static List<WrongObj> getWrongObjList(List<String> allLines, List<String> allObjects, Map<String, List<Integer>> voc, List<Case> cases) {
        List<WrongObj> wrongObjs = new ArrayList<>();
        for (Case caze : cases) {
            for (String obj : allObjects) {
                Pattern pattern = Pattern.compile(String.format(caze.getRegExp(), obj));
                List<Integer> indexes = voc.getOrDefault(obj, new ArrayList<>());
                for (Integer index : indexes) {
                    String currentLine = allLines.get(index);
                    Matcher matcher = pattern.matcher(currentLine);
                    if (matcher.find()) {
                        WrongObj wrongObj;
                        Optional<WrongObj> optionalWrongObj = WrongObj.findByName(obj, wrongObjs);
                        if (optionalWrongObj.isEmpty()) {
                            wrongObj = new WrongObj(obj);
                            wrongObjs.add(wrongObj);
                        } else {
                            wrongObj = optionalWrongObj.get();
                        }
                        wrongObj.getNumberLines().add(index + 1);
                    }
                }
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
                Matcher matcher = pattern.matcher(currentLine);
                if (matcher.find()) {
                    wo.getNumberLines().add(index + 1);
                }
            }
        }
        return wo.getNumberLines().size() > 0 ? wo : null;
    }

}