package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;

import java.util.*;

public class BuildCases {
    public static final String PRE_INCLUDE = "include";
    public static final String INCL_LEFT_QUOT = "[+] *include *\\(?[^`]\\b(?i)%s\\b";
    public static final String INCL_RIGHT_QUOT = "[+] *include *\\(?`?\\b(?i)%s\\b[^`]";// + є, пробіл(таб), include, пробіл(таб), ( може бути, ` може бути,  (?i) без врахування регістра, об'єкт, ` немає

    //    + Attributes
    public static final String PRE_ATTRIBUTES = "Attributes";
    public static final String ATTRIBUTES_LEFT_QUOT = "[+] *Attributes *\\(?[^`]\\b(?i)%s\\b";
    public static final String ATTRIBUTES_RIGHT_QUOT = "[+] *Attributes *\\(?`?(?i)%s\\b[^`]";

    public static final String PRE_DATA_ARR = "data (array";
    public static final String DATA_ARR_LEFT_QUOT = "[+] *data *\\(array\\[?[^`]\\b(?i)%s\\b";
    public static final String DATA_ARR__RIGHT_QUOT = "[+] *data *\\(array *\\[?`?\\b(?i)%s\\b[^`]";

    public static final String PRE_DATA = " + data (";
    public static final String DATA_LEFT_QUOT = "[+] *data *\\(?[^`]\\b(?i)%s\\b";
    public static final String DATA_RIGHT_QUOT = "[+] *data *\\(?`?\\b(?i)%s\\b[^`]";


    public static Case incl = new Case("include", "incl", PRE_INCLUDE);
    public static Case attr = new Case("attribute", "attr",PRE_ATTRIBUTES);
    public static Case dataArr = new Case("data_array", "d_arr",PRE_DATA_ARR);
    public static Case data = new Case("data", "data", PRE_DATA);


    private static Set<Case> cases = new HashSet<>();

    public static void addCaseInclude() {
        incl.getRegExpList().add(INCL_LEFT_QUOT);
        incl.getRegExpList().add(INCL_RIGHT_QUOT);
        cases.add(incl);
    }

    public static void addCaseAttribute() {
        attr.getRegExpList().add(ATTRIBUTES_LEFT_QUOT);
        attr.getRegExpList().add(ATTRIBUTES_RIGHT_QUOT);
        cases.add(attr);
    }

    public static void addCaseDataArr() {
        dataArr.getRegExpList().add(DATA_ARR_LEFT_QUOT);
        dataArr.getRegExpList().add(DATA_ARR__RIGHT_QUOT);
        cases.add(dataArr);
    }

    public static void addCaseData() {
        data.getRegExpList().add(DATA_LEFT_QUOT);
        data.getRegExpList().add(DATA_RIGHT_QUOT);
        cases.add(data);
    }

    public static List<Case> getCases() {
        return new ArrayList<>(cases);
    }
}
