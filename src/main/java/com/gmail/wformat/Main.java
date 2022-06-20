package com.gmail.wformat;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.entitys.HoldObjVoc;
import com.gmail.wformat.search.Vocabulary;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.threads.CallSearchObjVocab;
import com.gmail.wformat.threads.CallSearchWrong;
import com.gmail.wformat.util.BuildCases;
import com.gmail.wformat.util.Config;
import com.gmail.wformat.util.ReadWriteFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.gmail.wformat.util.Config.INPUT_FILE_NAME;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Long time = System.currentTimeMillis();
        Properties prop = Config.getPropertiesFile();

        String inputFileName;
        String formDate;
        String prefixFileName;
        String splitter;
        String manualRegExp;
        int isCaseInclude, isCaseAttribute, isCaseDataArray, isCaseData, isCaseFullOptions, isCaseManual;
        int numberThreads;
        try {
            isCaseInclude = Integer.valueOf(prop.getProperty(Config.CASE_INCLUDE));
            isCaseAttribute = Integer.valueOf(prop.getProperty(Config.CASE_ATTRIBUTE));
            isCaseDataArray = Integer.valueOf(prop.getProperty(Config.CASE_DATA_ARRAY));
            isCaseFullOptions = Integer.valueOf(prop.getProperty(Config.CASE_FULL_OPTIONS));
            isCaseData = Integer.valueOf(prop.getProperty(Config.CASE_DATA));
            isCaseManual = Integer.valueOf(prop.getProperty(Config.CASE_MANUAL_OPTIONS));
            manualRegExp = prop.getProperty(Config.REGULAR_MANUAL_OPTIONS);
            formDate = prop.getProperty(Config.OUT_FILE_FORM_DATE);
            prefixFileName = prop.getProperty(Config.OUT_FILE_PREFIX);
            splitter = prop.getProperty(Config.SPLITTER);
            inputFileName = prop.getProperty(INPUT_FILE_NAME);
            numberThreads = Integer.valueOf(prop.getProperty(Config.THREADS));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isCaseInclude = Config.CASE_INCLUDE_VAL;
            isCaseAttribute = Config.CASE_ATTRIBUTE_VAL;
            isCaseDataArray = Config.CASE_DATA_ARRAY_VAL;
            isCaseData = Config.CASE_DATA_VAL;
            isCaseFullOptions = Config.CASE_FULL_OPTIONS_VAL;
            isCaseManual = Config.CASE_MANUAL_OPTIONS_VAL;
            manualRegExp = Config.REGULAR_MANUAL_VAL;
            formDate = Config.OUT_FILE_FORM_DATE_VAL;
            prefixFileName = Config.OUT_FILE_PREFIX_VAL;
            splitter = Config.SPLITTER_VAL;
            inputFileName = Config.INPUT_FILE_NAME_VAL;
            numberThreads = Config.THREADS_VAL;
        }
        if (isCaseFullOptions == 0 && isCaseInclude == 0 && isCaseAttribute == 0 && isCaseDataArray == 0 && isCaseData == 0 && isCaseManual == 0) {
            System.out.println("\nне обрано жодного кейсу");
            return;
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
        if (isCaseFullOptions == 1) {
            BuildCases.addFullOptions();
            pullNameCases.add(BuildCases.fullOptions.getName());
        }
        if (isCaseManual == 1) {
            BuildCases.addManualOptions(manualRegExp);
            pullNameCases.add(BuildCases.manualOptions.getName());
            System.out.println(manualRegExp);
        }

        List<String> allLines = ReadWriteFile.readInputFile(inputFileName);
        List<WrongObj> listWrongObj = new ArrayList<>();
        HoldObjVoc holdObjVoc = new HoldObjVoc();
        List<Case> cases = BuildCases.getCases();

//        якщо вказано декілька потоків:
        if (numberThreads > 1) {
            ExecutorService ex = Executors.newFixedThreadPool(numberThreads);
            List<CallSearchObjVocab> taskObjVoc = new ArrayList<>();
            for (int i = 0; i < numberThreads; i++) {
                CallSearchObjVocab callSearchObjVocab = new CallSearchObjVocab(allLines, i, numberThreads);
                taskObjVoc.add(callSearchObjVocab);
            }
            List<Future<HoldObjVoc>> futureObjVoc = ex.invokeAll(taskObjVoc);
            for (Future f : futureObjVoc) {
                HoldObjVoc tempHoldObjVoc = (HoldObjVoc) f.get();
                holdObjVoc.getListAllObj().addAll(tempHoldObjVoc.getListAllObj());

                Map<String, List<Integer>> tempMap = tempHoldObjVoc.getVoc().getMap();
                Map<String, List<Integer>> map = holdObjVoc.getVoc().getMap();

                for (String key : tempMap.keySet()) {
                    if (map.containsKey(key)) {
                        map.get(key).addAll(tempMap.get(key));
                    } else {
                        map.put(key, tempMap.get(key));
                    }
                }
            }
            System.out.println("знайдено об'єктів: " + holdObjVoc.getListAllObj().size());
            System.out.println("сформовано словник, розміром: " + holdObjVoc.getVoc().getMap().size());

            List<CallSearchWrong> tasks = new ArrayList<>();
            for (int i = 0; i < numberThreads; i++) {
                tasks.add(new CallSearchWrong(allLines, holdObjVoc.getListAllObj(), cases, holdObjVoc.getVoc(), i, numberThreads));
            }
            List<Future<List<WrongObj>>> futures = ex.invokeAll(tasks);
            ex.shutdown();
            for (Future f : futures) {
                listWrongObj.addAll((List<WrongObj>) f.get());
            }


        } else {// в однопотоковому режимі
            var vocabulary = new Vocabulary();
            vocabulary.fillFromAllLines(allLines);

            List<String> allObjs = AllObjects.getList(allLines, 0, 1);
            listWrongObj = WrongFindRegular.getWrongObjList(allLines, allObjs, vocabulary, cases);

            System.out.println("знайдено об'єктів: " + allObjs.size());
            System.out.println("сформовано словник, розміром: " + vocabulary.getMap().size());
        }

        ReadWriteFile.write(listWrongObj, pullNameCases, inputFileName, prefixFileName, formDate, allLines);
        if (listWrongObj.size() > 0) {
            int count = listWrongObj.stream().map(s -> s.getNumberLines().size()).mapToInt(i -> i).sum();
            System.out.println(String.format("\nЗнайдено %s помилок", count));
        } else {
            System.out.println("\nпомилок не знайдено");
        }
        System.out.println("Тривалість " + (System.currentTimeMillis() - time) / 1000 + " сек.");
    }
}