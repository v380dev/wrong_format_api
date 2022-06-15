package com.gmail.wformat;

import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.util.BuildCases;
import com.gmail.wformat.util.ReadWriteFile;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Long time = System.currentTimeMillis();
        List<String> allLines = ReadWriteFile.readInputFile("apiary_test.apib");

        List<String> allObj = AllObjects.getList(allLines);
//        BuildCases.addCaseInclude();
        BuildCases.addCaseAttribute();
        BuildCases.addCaseDataArr();
        List<WrongObj> wObj = WrongFindRegular.getWrongObjList(allLines, allObj, BuildCases.getCases());

        if (wObj.size()>0) {
            wObj.stream().forEach(System.out::println);
        } else {
            System.out.println("помилок не знайдено");
        }


        System.out.println("пошук зайняв "+(System.currentTimeMillis() - time) / 1000 + " сек.");



/*

        String regular = "[+] *data *\\(array *\\[?`?\\b(?i)%s\\b[^`]";
        String obj = "Contracts_list_reimbursement";
        String line1 = "        + data (array[`Contracts_list_reimbursement])\n";
        Pattern pattern = Pattern.compile(String.format(regular,obj));
        Matcher matcher = pattern.matcher(line1);
        System.out.println(matcher.find());
*/

    }
}
