package com.gmail.wformat.entitys;

import java.util.*;
import java.util.stream.Collectors;

public class WrongObj implements Comparable {
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
        List<Integer> numbers = numberLines.stream().sorted().collect(Collectors.toList());
        return name +"@@@"+ numbers;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof WrongObj) {
        return this.getName().compareTo(((WrongObj) o).getName());
        }
        return 0;
    }
}
