package com.gmail.wformat.threads;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.WrongFindRegular;

import java.util.List;
import java.util.concurrent.Callable;

public class CallableImpl implements Callable<List<WrongObj>> {
    List<String> allLines;
    List<Case> cases;
    int startPosForObjSearch;
    int quentThr;

    public CallableImpl(List<String> allLines,
                        List<Case> cases,
                        int startPosForObjSearch,
                        int endPosForObjSearch) {
        this.allLines = allLines;
        this.cases = cases;
        this.startPosForObjSearch = startPosForObjSearch;
        this.quentThr = endPosForObjSearch;
    }

    @Override
    public List<WrongObj> call() throws Exception {
        Thread t = Thread.currentThread();//log
        System.out.println(t.getName() + " starts");//log
        List<String> allObj = AllObjects.getList(allLines, startPosForObjSearch, quentThr);
        List<WrongObj> wrongObjs = WrongFindRegular.getWrongObjList(allLines, allObj, cases);
        System.out.println(t.getName() + " end");//log
        return wrongObjs;
    }
}
