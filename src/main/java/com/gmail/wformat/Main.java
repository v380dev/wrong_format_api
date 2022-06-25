package com.gmail.wformat;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.HoldObjVoc;
import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.Vocabulary;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.threads.CallSearchObjVocab;
import com.gmail.wformat.threads.CallSearchWrong;
import com.gmail.wformat.util.Config;
import com.gmail.wformat.util.ReadWriteFile;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        long time = System.currentTimeMillis();
        var configContext = new ClassPathXmlApplicationContext("config/config_context.xml");
        Config config = configContext.getBean("config", Config.class);
        Properties prop = config.getPropertiesFile();
        var caseContext = new ClassPathXmlApplicationContext("regex/case_context.xml");
        var utilContext = new ClassPathXmlApplicationContext("regex/util_context.xml");

        String inputFileName;
        String formDate;
        String prefixFileName;
        int isCaseInclude, isCaseAttribute, isCaseDataArray, isCaseData, isCaseFullOptions;
        int numberThreads;
        try {
            isCaseInclude = Integer.parseInt(prop.getProperty(config.CASE_INCLUDE));
            isCaseAttribute = Integer.parseInt(prop.getProperty(config.CASE_ATTRIBUTE));
            isCaseDataArray = Integer.parseInt(prop.getProperty(config.CASE_DATA_ARRAY));
            isCaseFullOptions = Integer.parseInt(prop.getProperty(config.CASE_FULL_OPTIONS));
            isCaseData = Integer.parseInt(prop.getProperty(config.CASE_DATA));
            formDate = prop.getProperty(config.OUT_FILE_FORM_DATE);
            prefixFileName = prop.getProperty(config.OUT_FILE_PREFIX);
            inputFileName = prop.getProperty(config.INPUT_FILE_NAME);
            numberThreads = Integer.parseInt(prop.getProperty(config.THREADS));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isCaseInclude = config.CASE_INCLUDE_VAL;
            isCaseAttribute = config.CASE_ATTRIBUTE_VAL;
            isCaseDataArray = config.CASE_DATA_ARRAY_VAL;
            isCaseData = config.CASE_DATA_VAL;
            isCaseFullOptions = config.CASE_FULL_OPTIONS_VAL;
            formDate = config.OUT_FILE_FORM_DATE_VAL;
            prefixFileName = config.OUT_FILE_PREFIX_VAL;
            inputFileName = config.INPUT_FILE_NAME_VAL;
            numberThreads = config.THREADS_VAL;
        }
        if (isCaseFullOptions == 0 && isCaseInclude == 0 && isCaseAttribute == 0 && isCaseDataArray == 0 && isCaseData == 0) {
            System.out.println("\nне обрано жодного кейсу");
            return;
        }

        List<Case> cases = new ArrayList<>();
        if (isCaseInclude == 1) {
            cases.add(caseContext.getBean("include", Case.class));
        }
        if (isCaseAttribute == 1) {
            cases.add(caseContext.getBean("attribute", Case.class));
        }
        if (isCaseDataArray == 1) {
            cases.add(caseContext.getBean("data_array", Case.class));
        }
        if (isCaseData == 1) {
            cases.add(caseContext.getBean("data", Case.class));
        }
        if (isCaseFullOptions == 1) {
            cases.add(caseContext.getBean("fullOptions", Case.class));
        }

        Vocabulary vocabulary = utilContext.getBean("vocabulary", Vocabulary.class);
        AllObjects allObj = utilContext.getBean("allObj", AllObjects.class);

        List<String> allLines = ReadWriteFile.readInputFile(inputFileName);
        List<WrongObj> listWrongObj = new ArrayList<>();

//        якщо вказано декілька потоків:
        if (numberThreads > 1) {
            HoldObjVoc holdObjVoc = new HoldObjVoc(vocabulary);
            ExecutorService ex = Executors.newFixedThreadPool(numberThreads);
            List<CallSearchObjVocab> taskObjVoc = new ArrayList<>();
            for (int i = 0; i < numberThreads; i++) {
                CallSearchObjVocab callSearchObjVocab = new CallSearchObjVocab(allLines, i, numberThreads, vocabulary, allObj);
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
            vocabulary.fillFromAllLines(allLines);

            List<String> allObjs = allObj.getList(allLines, 0, 1);
            listWrongObj = WrongFindRegular.getWrongObjList(allLines, allObjs, vocabulary, cases);

            System.out.println("знайдено об'єктів: " + allObjs.size());
            System.out.println("сформовано словник, розміром: " + vocabulary.getMap().size());
        }

        ReadWriteFile.write(listWrongObj, cases, inputFileName, prefixFileName, formDate, allLines);
        if (listWrongObj.size() > 0) {
            int count = listWrongObj.stream().map(s -> s.getNumberLines().size()).mapToInt(i -> i).sum();
            System.out.println(String.format("\nЗнайдено %s помилок", count));
        } else {
            System.out.println("\nпомилок не знайдено");
        }
        System.out.println("Тривалість " + (System.currentTimeMillis() - time) / 1000 + " сек.");
    }
}