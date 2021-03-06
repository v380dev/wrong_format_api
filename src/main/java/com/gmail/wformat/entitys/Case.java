package com.gmail.wformat.entitys;

import java.util.Objects;

public class Case {
    private final String name;
    private final String prefix;
    private final String regExp;

    public Case(String name, String prefix, String regExp) {
        this.name = name;
        this.prefix = prefix;
        this.regExp = regExp;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRegExp() {
        return regExp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(name, aCase.name) &&
                Objects.equals(prefix, aCase.prefix) &&
                Objects.equals(regExp, aCase.regExp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, regExp);
    }

}