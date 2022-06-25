package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;
import com.gmail.wformat.entitys.WrongObj;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReadWriteFile {

    private static String generateNameFile(String prefixFileName, List<Case> cases, String formDate) {
        String date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formDate);
            date = formatter.format(new Date());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            date = formDate;
        }
        StringBuilder nameCases = new StringBuilder();
        for (Case cs : cases) {
            nameCases.append(cs.getName() + "_");
        }
        return String.format("%s_%s%s.txt", prefixFileName, nameCases, date);
    }

    public static String write(List<WrongObj> wObjs, List<Case> cases, String inputFileName, String prefixFileName, String formDate, List<String> allLines) {
        String nameInputFile = generateNameFile(prefixFileName, cases, formDate);
        try (Writer w = new FileWriter(nameInputFile)) {
            StringBuilder sb = new StringBuilder("Перевірено файл: ");
            sb.append(inputFileName + "\n\n");
            if (wObjs.size() == 0) {
                w.write(sb.toString());
                w.write("Помилок не знайдено");
            } else {
                sb.append(generateTextForReport(wObjs, cases, allLines));
                w.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameInputFile;
    }

    private static String generateTextForReport(List<WrongObj> wObjs, List<Case> cases, List<String> allLines) {
        StringBuilder text = new StringBuilder();
        text.append("За кейсами: ");
        text.append(cases.stream().map(Case::getName).collect(Collectors.joining(", ", "", ";\n")));
        int count = wObjs.stream().map(s -> s.getNumberLines().size()).mapToInt(i -> i).sum();
        text.append("Знайдено співпадінь: " + count + "\n\n");
        List<WrongObj> sortedWObj = wObjs.stream().sorted(WrongObj::compareTo).collect(Collectors.toList());
        for (WrongObj wo : sortedWObj) {
            List<Integer> numbers = wo.getNumberLines().stream().sorted().collect(Collectors.toList());
            text.append(wo.getName() + '\n');
            for (int numberLine : numbers) {
                text.append("  " + numberLine + " \"" + allLines.get(numberLine - 1).trim() + "\"\n");
            }
            text.append('\n');
        }
        return text.toString();
    }


    public static List<String> readInputFile(String nameFiles) throws IOException {
        List<String> listAllLines = new ArrayList<>();
        try (var br = new BufferedReader(new FileReader(nameFiles))) {
            String line;
            while ((line = br.readLine()) != null) {
                listAllLines.add(line);
            }
        }
        return listAllLines;
    }
}