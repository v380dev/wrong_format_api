package com.gmail.wformat.search;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WrongFindRegularTest {
    static List<String> listAllLines = new ArrayList<>();
    static List<String> allObjs = new ArrayList<>();
    static List<Case> cases = new ArrayList<>();
    static Vocabulary voc = new Vocabulary();

    @BeforeAll
    static void getAllLines() {
        listAllLines.add("+ `Test0` test1 test2!");
        listAllLines.add("+ Test3 test4 test5,");
        listAllLines.add("+ Test6 test5 test8");
        listAllLines.add("+ Test9 test10 test11");
    }

    @BeforeAll
    static public void getAllObjs() {
        allObjs.add("Test0");
        allObjs.add("test5");
    }

    @BeforeAll
    static public void getCases() {
        String regular = "(?<!`)\\b%1$s\\b(?!`)";//вважаємо помилкою об'єкт без лапок
        Case case1 = new Case("testCase", "+", "", regular);
        cases.add(case1);
    }

    @BeforeAll
    static public void getVocabulary() {
        List<Integer> t0 = new ArrayList<>();
        List<Integer> t1 = new ArrayList<>();
        List<Integer> t2 = new ArrayList<>();
        List<Integer> t3 = new ArrayList<>();
        List<Integer> t4 = new ArrayList<>();
        List<Integer> t5 = new ArrayList<>();
        List<Integer> t6 = new ArrayList<>();
        List<Integer> t8 = new ArrayList<>();
        List<Integer> t9 = new ArrayList<>();
        List<Integer> t10 = new ArrayList<>();
        List<Integer> t11 = new ArrayList<>();
        t0.add(0);
        t1.add(0);
        t2.add(0);
        t3.add(1);
        t4.add(1);
        t5.add(1);
        t5.add(2);
        t6.add(2);
        t8.add(2);
        t9.add(3);
        t10.add(3);
        t11.add(3);
        voc.getMap().put("Test0", t0);
        voc.getMap().put("test1", t1);
        voc.getMap().put("test2", t2);
        voc.getMap().put("Test3", t3);
        voc.getMap().put("test4", t4);
        voc.getMap().put("test5", t5);
        voc.getMap().put("test6", t6);
        voc.getMap().put("test8", t8);
        voc.getMap().put("test9", t9);
        voc.getMap().put("test10", t10);
        voc.getMap().put("test11", t11);
    }

    @Test
    void getWrongObjListMonoThread() {
        List<WrongObj> wol = WrongFindRegular.getWrongObjList(listAllLines, allObjs, voc, cases);
        String actual = wol.get(0).getName();
        assertEquals("test5", actual);
    }

    @Test
    void getWrongObjListMonoThread2() {
        List<WrongObj> wol = WrongFindRegular.getWrongObjList(listAllLines, allObjs, voc, cases);
        int quantity = wol.get(0).getNumberLines().size();
        assertEquals(2, quantity);
    }

    @Test
    void getWrongObjListMultiThreads() {
        WrongObj wo = WrongFindRegular.getWrongObj(listAllLines, "test5", voc, cases);
        assertTrue(wo.getNumberLines().containsAll(Arrays.asList(1+1,2+1)));
    }

    @Test
    void getWrongObjListMultiThreads2() {
        WrongObj wo = WrongFindRegular.getWrongObj(listAllLines, "Test0", voc, cases);
        assertNull(wo);
    }
}