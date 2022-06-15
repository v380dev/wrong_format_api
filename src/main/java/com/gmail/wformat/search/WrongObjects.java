package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongObjects {

    public static List<WrongObj> getWrongObjList(List<String> allLines, List<String> allObjects, List<Case> cases) {
        List<WrongObj> wrongObjs = new ArrayList<>();
        String currentLine;
        for (int i = 0; i < allLines.size(); i++) {
            currentLine = allLines.get(i);
            for (Case cs : cases) {
                for (String regExp : cs.getRegExp()) {
                    for (String obj : allObjects) {
                        Pattern pattern = Pattern.compile(String.format(regExp, obj));
                        Matcher matcher = pattern.matcher(currentLine);
                        if (matcher.find()) {
                            WrongObj wrongObj;
                            Optional<WrongObj> optionalWrongObj = WrongObj.findByName(obj, wrongObjs);
                            if(optionalWrongObj.isEmpty()) {
                                wrongObj = new WrongObj(obj);
                                wrongObjs.add(wrongObj);
                            } else {
                                wrongObj = optionalWrongObj.get();
                            }
                            wrongObj.getNumberLines().add(i+1);
                        }
                    }
                }
            }
        }
        return wrongObjs;
    }

}