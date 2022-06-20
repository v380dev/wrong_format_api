package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildCases {
    public static final String PRE_INCLUDE = "include";
    //    public static final String INCL_QUOTES = "\\+\\ *include\\ *\\(?.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";
    public static final String INCL_QUOTES = "(?<=\\+ *include *[^\\(]*)(((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`)))";

    public static final String PRE_ATTRIBUTES = "Attributes";
    public static final String ATTRIBUTES_QUOTES = "\\+\\ *Attributes\\ *\\(.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_DATA_ARR = "data (array";
    public static final String DATA_ARR_QUOTES = "\\+\\ *data\\ *\\( ?array ?\\[.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_DATA = " + data (";
    public static final String DATA_QUOTES = "\\+\\ *data\\ *\\( ?.*((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";

    public static final String PRE_FULL_OPTIONS = "+";
    //    public static final String FULL_OPTIONS_QUOTES = "((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";
//    public static final String FULL_OPTIONS_QUOTES =/ "([\\(]\\b%1$s\\b)|(\\b%1$s\\b[\\)])";//якщо слово біля дужки, всередині
    public static final String FULL_OPTIONS_QUOTES = "(^[^-#]*(?<!`)(\\b%1$s\\b))|(^[^-#]*(\\b%1$s\\b)(?!`))";//якщо слово після тіре не потрапляє у виборку

//    public static final String EXCLUDE_DESCRIPTIONS = "((?<!`)\\b%1$s\\b \\b\\w+\\b \\b\\w+\\b(?!`))|((?<!`)\\b\\w+\\b \\b%1$s\\b \\b\\w+\\b(?!`))|((?<!`)\\b\\w+\\b \\b\\w+\\b \\b%1$s\\b(?!`))";
//    public static final String EXCLUDE_DESCRIPTIONS = "(\\(%1$s[,\\)])|(, %1$s\\))";


    public static Case incl;
    public static Case attr;
    public static Case dataArr;
    public static Case data;
    public static Case fullOptions;
    public static Case manualOptions;


    private static Set<Case> cases = new HashSet<>();

    public static void addCaseInclude() {
        incl = new Case("include", "incl", PRE_INCLUDE, INCL_QUOTES);
        cases.add(incl);
    }

    public static void addCaseAttribute() {
        attr = new Case("attribute", "attr", PRE_ATTRIBUTES, ATTRIBUTES_QUOTES);
        cases.add(attr);
    }

    public static void addCaseDataArr() {
        dataArr = new Case("data_array", "d_arr", PRE_DATA_ARR, DATA_ARR_QUOTES);
        cases.add(dataArr);
    }

    public static void addCaseData() {
        data = new Case("data", "data", PRE_DATA, DATA_QUOTES);
        cases.add(data);
    }

    public static void addFullOptions() {
        fullOptions = new Case("fullOptions", "full", PRE_FULL_OPTIONS, FULL_OPTIONS_QUOTES);
        cases.add(fullOptions);
    }

    public static void addManualOptions(String regExp) {
        manualOptions = new Case("manualOptions", "manual", PRE_FULL_OPTIONS, regExp);
        cases.add(manualOptions);
    }

    public static List<Case> getCases() {
        return new ArrayList<>(cases);
    }
}