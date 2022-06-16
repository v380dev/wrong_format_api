package com.gmail.wformat.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Case {
    private String name;
    private String prefix;
    private String preFilter;
    private List<String> regExpList;

    public Case(String name, String prefix, String preFilter) {
        this.name = name;
        this.prefix = prefix;
        this.preFilter = preFilter;
        this.regExpList = new ArrayList<>();
    }

    public String getPreFilter() {
        return preFilter;
    }


    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getRegExpList() {
        return regExpList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(name, aCase.name) &&
                Objects.equals(prefix, aCase.prefix) &&
                Objects.equals(preFilter, aCase.preFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, preFilter);
    }
}
