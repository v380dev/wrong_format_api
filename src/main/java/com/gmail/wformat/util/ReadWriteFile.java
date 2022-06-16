package com.gmail.wformat.util;

import com.gmail.wformat.entitys.WrongObj;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReadWriteFile {

    private static String generateNameFile(String prefixFileName, List<String> pullNameCases, String formDate) {
        String date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formDate);
            date = formatter.format(new Date());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            date = formDate;
        }
        StringBuilder nameCases = new StringBuilder();
        for (String name : pullNameCases) {
            nameCases.append(name + "_");
        }
        return String.format("%s_%s%s.txt", prefixFileName, nameCases, date);
    }

    public static String write(List<WrongObj> wObjs, List<String> pullNameCases, String inputFileName, String splitter, String prefixFileName, String formDate) {
        String nameInputFile = generateNameFile(prefixFileName, pullNameCases, formDate);
        try (Writer w = new FileWriter(nameInputFile)) {
            StringBuilder sb = new StringBuilder("Перевірено файл: ");
            sb.append(inputFileName + "\n\n");

            System.out.println(wObjs.size());
            if (wObjs.size() == 0) {
                w.write(sb.toString());
                w.write("Помилок не знайдено");
            } else {
                int count = wObjs.stream().map(s -> s.getNumberLines().size()).mapToInt(i -> i).sum();
                sb.append(String.format("Знайдено %s помилок\n\n", count));
                wObjs = wObjs.stream().sorted().collect(Collectors.toList());
                for (WrongObj obj : wObjs) {
                    sb.append(obj.getName());
                    sb.append(splitter);
                    sb.append(obj.printNumbers() + '\n');
                }
                w.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameInputFile;
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
