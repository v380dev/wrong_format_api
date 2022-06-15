package com.gmail.wformat.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Case {
//    private String name;
    private List<String> regExpList;

    public Case(/*String name*/) {
//        this.name = name;
        this.regExpList = new ArrayList<>();
    }

/*
    public String getName() {
        return name;
    }
*/

/*
    public void setName(String name) {
        this.name = name;
    }
*/

    public List<String> getRegExpList() {
        return regExpList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(regExpList, aCase.regExpList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regExpList);
    }

/*
    public void setRegExpList(List<String> regExpList) {
        this.regExpList = regExpList;
    }
*/
}
