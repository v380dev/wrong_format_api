package com.gmail.wformat.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrongObj {
    String name;
    List<Integer> numberLines;

    public WrongObj(String name) {
        this.name = name;
        this.numberLines = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Integer> getNumberLines() {
        return numberLines;
    }

    public static Optional<WrongObj> findByName(String name, List<WrongObj>wrongObjList) {
        for(WrongObj wrongObj : wrongObjList) {
            if(wrongObj.getName().equals(name)) {
                return Optional.of(wrongObj);
            }
        }
        return Optional.empty();
    }
}
