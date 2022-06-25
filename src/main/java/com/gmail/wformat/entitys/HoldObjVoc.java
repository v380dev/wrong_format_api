package com.gmail.wformat.entitys;

import com.gmail.wformat.search.Vocabulary;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

//для багатопотокового виконання: щоб отримати результат у вигляді одного об'єкту
public class HoldObjVoc {
    private List<String> listAllObj;
    private Vocabulary voc;

    public HoldObjVoc(Vocabulary vocabulary) {
        this.listAllObj = new ArrayList<>();
        this.voc = vocabulary;
    }

    public List<String> getListAllObj() {
        return listAllObj;
    }

    public Vocabulary getVoc() {
        return voc;
    }
}
