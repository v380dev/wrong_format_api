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
    //    public static final String FULL_OPTIONS_QUOTES = "((?<!`)\\b%1$s\\b)|(\\b%1$s\\b(?!`))";
    public static final String FULL_OPTIONS_QUOTES = "([\\(]\\b%1$s\\b)|(\\b%1$s\\b[\\)])";//якщо слово біля дужки, всередині

//    public static final String EXCLUDE_DESCRIPTIONS = "((?<!`)\\b%1$s\\b \\b\\w+\\b \\b\\w+\\b(?!`))|((?<!`)\\b\\w+\\b \\b%1$s\\b \\b\\w+\\b(?!`))|((?<!`)\\b\\w+\\b \\b\\w+\\b \\b%1$s\\b(?!`))";
//    public static final String EXCLUDE_DESCRIPTIONS = "(\\(%1$s[,\\)])|(, %1$s\\))";


    public static Case incl;// = new Case("include", "incl", PRE_INCLUDE, INCL_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
    public static Case attr;// = new Case("attribute", "attr", PRE_ATTRIBUTES, ATTRIBUTES_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
    public static Case dataArr;// = new Case("data_array", "d_arr", PRE_DATA_ARR, DATA_ARR_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
    public static Case data;// = new Case("data", "data", PRE_DATA, DATA_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
    public static Case fullOptions;// = new Case("fullOptions", "full", PRE_FULL_OPTIONS, FULL_OPTIONS_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
    public static Case manualOptions;// = new Case("manualOptions", "manual", PRE_FULL_OPTIONS, null /*, EXCLUDE_DESCRIPTIONS*/);


    private static Set<Case> cases = new HashSet<>();

    public static void addCaseInclude() {
        incl = new Case("include", "incl", PRE_INCLUDE, INCL_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
        cases.add(incl);
    }

    public static void addCaseAttribute() {
        attr = new Case("attribute", "attr", PRE_ATTRIBUTES, ATTRIBUTES_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
        cases.add(attr);
    }

    public static void addCaseDataArr() {
        dataArr = new Case("data_array", "d_arr", PRE_DATA_ARR, DATA_ARR_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
        cases.add(dataArr);
    }

    public static void addCaseData() {
        data = new Case("data", "data", PRE_DATA, DATA_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
        cases.add(data);
    }

    public static void addFullOptions() {
        fullOptions = new Case("fullOptions", "full", PRE_FULL_OPTIONS, FULL_OPTIONS_QUOTES/*, EXCLUDE_DESCRIPTIONS*/);
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