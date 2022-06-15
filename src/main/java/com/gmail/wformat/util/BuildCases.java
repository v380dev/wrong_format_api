package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;

import java.util.*;

public class BuildCases {
    public static final String INCL_LEFT_QUOT = "[+] *include *\\(?[^`]\\b(?i)%s\\b";
    public static final String INCL_RIGHT_QUOT = "[+] *include *\\(?`?\\b(?i)%s\\b[^`]";// + є, пробіл(таб), include, пробіл(таб), ( може бути, ` може бути,  (?i) без врахування регістра, об'єкт, ` немає

    //    + Attributes
    public static final String ATTRIBUTES_LEFT_QUOT = "[+] *Attributes *\\(?[^`]\\b(?i)%s\\b";
    public static final String ATTRIBUTES_RIGHT_QUOT = "[+] *Attributes *\\(?`?(?i)%s\\b[^`]";

    public static final String DATA_ARR_LEFT_QUOT = "[+] *data *\\(array\\[?[^`]\\b(?i)%s\\b";
    public static final String DATA_ARR__RIGHT_QUOT = "[+] *data *\\(array *\\[?`?\\b(?i)%s\\b[^`]";


    public static Case incl = new Case();
    public static Case attr = new Case();
    public static Case dataArr = new Case();


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
        attr.getRegExpList().add(DATA_ARR_LEFT_QUOT);
        attr.getRegExpList().add(DATA_ARR__RIGHT_QUOT);
        cases.add(attr);
    }

    public static List<Case> getCases() {
        return new ArrayList<>(cases);
    }
}
