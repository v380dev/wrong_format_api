package com.gmail.wformat.entitys;

import java.util.*;
import java.util.stream.Collectors;

public class WrongObj implements Comparable {
    String name;
    String splitter;
    Set<Integer> numberLines;

    public WrongObj(String name) {
        this.name = name;
        this.splitter = "=";
        this.numberLines = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getNumberLines() {
        return numberLines;
    }

    public static Optional<WrongObj> findByName(String name, List<WrongObj> wrongObjList) {
        for (WrongObj wrongObj : wrongObjList) {
            if (wrongObj.getName().equals(name)) {
                return Optional.of(wrongObj);
            }
        }
        return Optional.empty();
    }

    public String printNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>(numberLines);
        numbers.sort((i1,i2)->i1-i2);
        return numbers.toString();
    }

    @Override
    public String toString() {
        List<Integer> numbers = numberLines.stream().sorted().collect(Collectors.toList());
        return name + splitter + numbers;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof WrongObj) {
            return this.getName().compareTo(((WrongObj) o).getName());
        }
        return 0;
    }
}
