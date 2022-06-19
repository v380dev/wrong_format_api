package com.gmail.wformat.threads;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.search.Vocabulary;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.WrongFindRegular;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CallSearchWrong implements Callable<List<WrongObj>> {
    List<String> allObj;
    Vocabulary vocabulary;
    List<String> allLines;
    List<Case> cases;
    int startPosForObjSearch;
    int quentThr;

    public CallSearchWrong(List<String> allLines,
                           List<String> allObj,
                           List<Case> cases,
                           Vocabulary vocabulary,
                           int startPosForObjSearch,
                           int endPosForObjSearch) {
        this.allLines = allLines;
        this.cases = cases;
        this.startPosForObjSearch = startPosForObjSearch;
        this.quentThr = endPosForObjSearch;
        this.allObj = allObj;
        this.vocabulary = vocabulary;
    }

    @Override
    public List<WrongObj> call() throws Exception {
        Thread t = Thread.currentThread();//log
        System.out.println(t.getName() + "   starts search mistakes");//log

        List<WrongObj> wrongObjs = new ArrayList<>();
        for (int i = startPosForObjSearch; i < allObj.size(); i += quentThr) {
            String nameObj = allObj.get(i);
            WrongObj wo = WrongFindRegular.getWrongObj(allLines, nameObj, vocabulary, cases);
            if (wo != null) {
                wrongObjs.add(wo);
            }
        }
        System.out.println(t.getName() + "   finished looking for mistakes");//log
        return wrongObjs;
    }
}
