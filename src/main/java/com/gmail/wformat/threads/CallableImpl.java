package com.gmail.wformat.threads;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.WrongFindRegular;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CallableImpl implements Callable<List<WrongObj>> {
    List<String> allLines;
    List<String> allObjects;
    List<Case> cases;
    int startPosition;
    int endPosition;

    public CallableImpl(List<String> allLines, List<String> allObjects, List<Case> cases, int startPosition, int endPosition) {
        this.allLines = allLines;
        this.allObjects = allObjects;
        this.cases = cases;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public List<WrongObj> call() throws Exception {
        Thread t = Thread.currentThread();//log
        System.out.println(t.getName() + " starts");//log
        List<WrongObj> wrongObjs = new ArrayList<>();
        wrongObjs = WrongFindRegular.getWrongObjList(allLines, allObjects, cases, startPosition, endPosition);
        System.out.println(t.getName() + " end");//log
        return wrongObjs;
    }
}
