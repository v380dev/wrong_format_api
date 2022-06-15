package com.gmail.wformat.entitys;

import java.util.*;

public class WrongObj {
    String name;
    Set<Integer> numberLines;

    public WrongObj(String name) {
        this.name = name;
        this.numberLines = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getNumberLines() {
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

    @Override
    public String toString() {
        return name +"@@@"+ numberLines;
    }
}
