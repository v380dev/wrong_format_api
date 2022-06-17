package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.util.BuildCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongFindRegular {

    public static List<WrongObj> getWrongObjList(List<String> allLines, List<String> allObjects, List<Case> cases, int startPosition, int endPosition) {
        List<WrongObj> wrongObjs = new ArrayList<>();
        String currentLine;

        for (int i = 0; i < allLines.size(); i++) {
            currentLine = allLines.get(i);
            for (Case cs : cases) {
                if (!currentLine.contains(cs.getPreFilter())) {
                    continue;
                }
                for (int j=startPosition;j<endPosition;j++) {
                    Pattern pattern = Pattern.compile(String.format(cs.getRegExp(), allObjects.get(j)));
                    Matcher matcher = pattern.matcher(currentLine);
                    if (matcher.find()) {
                        Pattern patternExclDescript = Pattern.compile(String.format(cs.getExcludeDescription(), allObjects.get(j)));
                        Matcher matcherExclDescript = patternExclDescript.matcher(currentLine);
                        if (!matcherExclDescript.find()) {
                            WrongObj wrongObj;
                            Optional<WrongObj> optionalWrongObj = WrongObj.findByName(allObjects.get(j), wrongObjs);
                            if (optionalWrongObj.isEmpty()) {
                                wrongObj = new WrongObj(allObjects.get(j));
                                wrongObjs.add(wrongObj);
                            } else {
                                wrongObj = optionalWrongObj.get();
                            }
                            wrongObj.getNumberLines().add(i + 1);
                        }
                    }
                }
            }
        }
        return wrongObjs;
    }
}