package com.gmail.wformat.entitys;

import com.gmail.wformat.search.Vocabulary;

import java.util.ArrayList;
import java.util.List;

//для багатопотокового виконання: щоб отримати результат у вигляді одного об'єкту
public class HoldObjVoc {
    private List<String> listAllObj;
    private Vocabulary voc;

    public HoldObjVoc() {
        this.listAllObj = new ArrayList<>();
        this.voc = new Vocabulary();
    }

    public List<String> getListAllObj() {
        return listAllObj;
    }

    public Vocabulary getVoc() {
        return voc;
    }
}
