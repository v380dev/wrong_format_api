package com.gmail.wformat.entitys;

import java.util.List;

public class Case {
    private String name;
    private List<String> regExp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRegExp() {
        return regExp;
    }

    public void setRegExp(List<String> regExp) {
        this.regExp = regExp;
    }
}
