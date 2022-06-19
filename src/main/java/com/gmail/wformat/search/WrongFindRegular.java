package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.util.BuildCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongFindRegular {

    public static List<WrongObj> getWrongObjList(List<String> allLines, List<String> allObjects, List<Case> cases) {
        List<WrongObj> wrongObjs = new ArrayList<>();
        String currentLine;

        for (int i = 0; i < allLines.size(); i++) {
            currentLine = allLines.get(i);
            for (Case cs : cases) {
                if (!currentLine.contains(cs.getPreFilter())) {
                    continue;
                }
                for (String obj : allObjects) {
                    Pattern pattern = Pattern.compile(String.format(cs.getRegExp(), obj));
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
                        wrongObj.getNumberLines().add(i + 1);
                    }
                }
            }
        }
//        }
        return wrongObjs;
    }

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
                        wrongObj.getNumberLines().add(index+1);
                    }
                }
            }
        }
        return wrongObjs;
    }
}