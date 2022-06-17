package com.gmail.wformat.entitys;

import java.util.Objects;

public class Case {
    private final String name;
    private final String prefix;
    private final String preFilter;
    private final String regExp;
    private final String excludeDescription;

    public Case(String name, String prefix, String preFilter, String regExp, String excludeDescription) {
        this.name = name;
        this.prefix = prefix;
        this.preFilter = preFilter;
        this.regExp = regExp;
        this.excludeDescription = excludeDescription;
    }

    /*
    public Case(String name, String prefix, String preFilter, String regExp) {
        this.name = name;
        this.prefix = prefix;
        this.preFilter = preFilter;
        this.regExp = regExp;
    }
*/

    public String getPreFilter() {
        return preFilter;
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

    public String getExcludeDescription() {
        return excludeDescription;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(name, aCase.name) &&
                Objects.equals(prefix, aCase.prefix) &&
                Objects.equals(preFilter, aCase.preFilter) &&
                Objects.equals(regExp, aCase.regExp) &&
                Objects.equals(excludeDescription, aCase.excludeDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, preFilter, regExp, excludeDescription);
    }
}