package com.gmail.wformat;

import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.search.WrongFindString;
import com.gmail.wformat.util.BuildCases;
import com.gmail.wformat.util.ReadWriteFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        Long time = System.currentTimeMillis();
        List<String> allLines = ReadWriteFile.readInputFile("apiary_test.apib");

        List<String> allObj = AllObjects.getList(allLines);
//        BuildCases.addCaseInclude();
        BuildCases.addCaseAttribute();
        List<WrongObj> wObj = WrongFindRegular.getWrongObjList(allLines, allObj, BuildCases.getCases());

        if (wObj.size()>0) {
            wObj.stream().forEach(System.out::println);
        } else {
            System.out.println("помилок не знайдено");
        }


        System.out.println("пошук зайняв "+(System.currentTimeMillis() - time) / 1000 + " сек.");



//        allObj.stream().forEach(System.out::println);

//        System.out.println(RegExp.START_OBJ.get);

//        String test = "[+] *Attributes *`?\\(?%s\\b[^`]";

/*
        String regular = "#{1,} `";
        String obj = "Medication_request_Request";
        String line1 = "## `Medication_request_Request`";
        Pattern pattern = Pattern.compile(String.format(regular,obj));
        Matcher matcher1 = pattern.matcher(line1);
        System.out.println(matcher1.find());
*/

/*
        String line = "    + `medical_program_settings_text`: `Some text` (string, required) - justification of the possibility or impossibility of using the program on medication request with some settings\n";
        String obj = "Medical_program_settings";
        System.out.println(line.contains(String.format("`%s`", obj)));
*/

    }
}
