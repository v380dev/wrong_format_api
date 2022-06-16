package com.gmail.wformat;

import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.util.BuildCases;
import com.gmail.wformat.util.Config;
import com.gmail.wformat.util.ReadWriteFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.gmail.wformat.util.Config.INPUT_FILE_NAME;
import static com.gmail.wformat.util.Config.INPUT_FILE_NAME_VAL;

public class Main {
    public static void main(String[] args) throws IOException {
        Long time = System.currentTimeMillis();
        Properties prop = Config.getPropertiesFile();

        String inputFileName;
        String formDate;
        String prefixFileName;
        String splitter;
        int isCaseInclude, isCaseAttribute, isCaseDataArray, isCaseData;
        try {
            isCaseInclude = Integer.valueOf(prop.getProperty(Config.CASE_INCLUDE));
            isCaseAttribute = Integer.valueOf(prop.getProperty(Config.CASE_ATTRIBUTE));
            isCaseDataArray = Integer.valueOf(prop.getProperty(Config.CASE_DATA_ARRAY));
            isCaseData = Integer.valueOf(prop.getProperty(Config.CASE_DATA));
            formDate = prop.getProperty(Config.OUT_FILE_FORM_DATE);
            prefixFileName = prop.getProperty(Config.OUT_FILE_PREFIX);
            splitter = prop.getProperty(Config.SPLITTER);
            inputFileName = prop.getProperty(INPUT_FILE_NAME);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isCaseInclude = Config.CASE_INCLUDE_VAL;
            isCaseAttribute = Config.CASE_ATTRIBUTE_VAL;
            isCaseDataArray = Config.CASE_DATA_ARRAY_VAL;
            isCaseData = Config.CASE_DATA_VAL;
            formDate = prop.getProperty(Config.OUT_FILE_FORM_DATE_VAL);
            prefixFileName = prop.getProperty(Config.OUT_FILE_PREFIX_VAL);
            splitter = prop.getProperty(Config.SPLITTER_VAL);
            inputFileName = prop.getProperty(INPUT_FILE_NAME_VAL);
        }

        List<String> pullNameCases = new ArrayList<>();
        if (isCaseInclude == 1) {
            BuildCases.addCaseInclude();
            pullNameCases.add(BuildCases.incl.getName());
        }
        if (isCaseAttribute == 1) {
            BuildCases.addCaseAttribute();
            pullNameCases.add(BuildCases.attr.getName());
        }
        if (isCaseDataArray == 1) {
            BuildCases.addCaseDataArr();
            pullNameCases.add(BuildCases.dataArr.getName());
        }
        if (isCaseData == 1) {
            BuildCases.addCaseData();
            pullNameCases.add(BuildCases.data.getName());
        }

        List<String> allLines = ReadWriteFile.readInputFile(inputFileName);

        List<String> allObj = AllObjects.getList(allLines);
        List<WrongObj> wObjs = WrongFindRegular.getWrongObjList(allLines, allObj, BuildCases.getCases());

        ReadWriteFile.write(wObjs, pullNameCases, inputFileName, splitter, prefixFileName, formDate);

        if (wObjs.size() > 0) {
            int count = wObjs.stream().map(s -> s.getNumberLines().size()).mapToInt(i -> i).sum();
//            wObjs.stream().sorted().forEach(System.out::println);
            System.out.println(String.format("\nЗнайдено %s помилок", count));
        } else {
            System.out.println("\nпомилок не знайдено");
        }

        System.out.println("Тривалість " + (System.currentTimeMillis() - time) / 1000 + " сек.");
    }
}