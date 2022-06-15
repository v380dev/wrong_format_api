package com.gmail.wformat.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadWriteFile {

/*
    private static String generateNameFile(String nameField, String filePrefix, String formDate) {
        String date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formDate);
            date = formatter.format(new Date());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            date = formDate;
        }
        String fileName = String.format("%s_%s_%s.txt", filePrefix, nameField, date);
        return fileName;
    }
*/

/*
    public static String write(String nameField,
                             List<Endpoint> endpoints,
                             String splitter,
                             String filePrefix,
                             String formDate) throws IOException {
        String nameInputFile = generateNameFile(nameField, filePrefix, formDate);
        try (Writer w = new FileWriter(nameInputFile);) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < endpoints.size(); i++) {
                sb.append(endpoints.get(i).getNumberLine())
                        .append(splitter)
                        .append(endpoints.get(i).getName())
                        .append(splitter)
                        .append("REQ=")
                        .append(endpoints.get(i).getRequests())
                        .append(splitter)
                        .append("RES=")
                        .append(endpoints.get(i).getResponses())
                        .append('\n');
            }
            w.write(sb.toString());
        }
        return nameInputFile;
    }
*/

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
