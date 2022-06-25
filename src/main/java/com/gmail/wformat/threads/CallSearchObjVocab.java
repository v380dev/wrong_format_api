package com.gmail.wformat.threads;

import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.entitys.HoldObjVoc;
import com.gmail.wformat.search.Vocabulary;

import java.util.List;
import java.util.concurrent.Callable;

public class CallSearchObjVocab implements Callable<HoldObjVoc> {
    HoldObjVoc holdObjVoc;
    List<String> allLines;
    int startPosForObjSearch;
    int quentThr;
    AllObjects allObj;

    public CallSearchObjVocab(List<String> allLines,
                              int startPosForObjSearch,
                              int quentThr,
                              Vocabulary voc,
                              AllObjects allObj) {
        this.allLines = allLines;
        this.startPosForObjSearch = startPosForObjSearch;
        this.quentThr = quentThr;
        this.holdObjVoc = new HoldObjVoc(voc);
        this.allObj = allObj;
    }

    @Override
    public HoldObjVoc call() {
        Thread t = Thread.currentThread();//log
        System.out.println(t.getName() + "   starts search objects and fill vocabulary");//log
        for (int i = startPosForObjSearch; i < allLines.size(); i += quentThr) {
            String currentLine = allLines.get(i);
            holdObjVoc.getListAllObj().addAll(allObj.getListFromOneLine(currentLine));
            holdObjVoc.getVoc().fillFromOneLine(currentLine, i);
        }
        System.out.println(t.getName() + "   finished looking for objects and fill vocabulary");//log
        return holdObjVoc;
    }
}
