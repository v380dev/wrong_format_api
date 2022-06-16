package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildCases {
    public static final String PRE_INCLUDE = "include";
    public static final String INCL_QUOTES = "\\+\\ *include\\ *\\(?.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_ATTRIBUTES = "Attributes";
    public static final String ATTRIBUTES_QUOTES = "\\+\\ *Attributes\\ *\\(.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_DATA_ARR = "data (array";
    public static final String DATA_ARR_QUOTES = "\\+\\ *data\\ *\\( ?array ?\\[.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_DATA = " + data (";
    public static final String DATA_QUOTES = "\\+\\ *data\\ *\\( ?.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_FULL_OPTIONS = "+";
    public static final String FULL_OPTIONS_QUOTES = "((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";


    public static Case incl = new Case("include", "incl", PRE_INCLUDE, INCL_QUOTES);
    public static Case attr = new Case("attribute", "attr", PRE_ATTRIBUTES, ATTRIBUTES_QUOTES);
    public static Case dataArr = new Case("data_array", "d_arr", PRE_DATA_ARR, DATA_ARR_QUOTES);
    public static Case data = new Case("data", "data", PRE_DATA, DATA_QUOTES);
    public static Case fullOptions = new Case("fullOptions", "full", PRE_FULL_OPTIONS, FULL_OPTIONS_QUOTES);


    private static Set<Case> cases = new HashSet<>();

    public static void addCaseInclude() {
        cases.add(incl);
    }

    public static void addCaseAttribute() {
        cases.add(attr);
    }

    public static void addCaseDataArr() {
        cases.add(dataArr);
    }

    public static void addCaseData() {
        cases.add(data);
    }

    public static void addFullOptions() {
        cases.add(fullOptions);
    }

    public static List<Case> getCases() {
        return new ArrayList<>(cases);
    }
}