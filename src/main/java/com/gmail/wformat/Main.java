package com.gmail.wformat;

import com.gmail.wformat.entitys.WrongObj;
import com.gmail.wformat.search.AllObjects;
import com.gmail.wformat.search.WrongFindRegular;
import com.gmail.wformat.util.BuildCases;
import com.gmail.wformat.util.Config;
import com.gmail.wformat.util.ReadWriteFile;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gmail.wformat.util.Config.INPUT_FILE_NAME;

public class Main {
    public static void main(String[] args) throws IOException {
        Long time = System.currentTimeMillis();
        Properties prop = Config.getPropertiesFile();

        String inputFileName = prop.getProperty(INPUT_FILE_NAME);
        int isCaseInclude, isCaseAttribute, isCaseDataArray, isCaseData;
        try {
            isCaseInclude = Integer.valueOf(prop.getProperty(Config.CASE_INCLUDE));
            isCaseAttribute = Integer.valueOf(prop.getProperty(Config.CASE_ATTRIBUTE));
            isCaseDataArray = Integer.valueOf(prop.getProperty(Config.CASE_DATA_ARRAY));
            isCaseData = Integer.valueOf(prop.getProperty(Config.CASE_DATA));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isCaseInclude = Config.CASE_INCLUDE_VAL;
            isCaseAttribute = Config.CASE_ATTRIBUTE_VAL;
            isCaseDataArray = Config.CASE_DATA_ARRAY_VAL;
            isCaseData = Config.CASE_DATA_VAL;
        }

        if(isCaseInclude == 1) {
            BuildCases.addCaseInclude();
        }
        if(isCaseAttribute == 1) {
            BuildCases.addCaseAttribute();
        }
        if(isCaseDataArray == 1) {
            BuildCases.addCaseDataArr();
        }
        if(isCaseData == 1) {
            BuildCases.addCaseData();
        }

        List<String> allLines = ReadWriteFile.readInputFile(inputFileName);

        List<String> allObj = AllObjects.getList(allLines);
        List<WrongObj> wObj = WrongFindRegular.getWrongObjList(allLines, allObj, BuildCases.getCases());

        if (wObj.size() > 0) {
            int count = wObj.stream().map(s->s.getNumberLines().size()).mapToInt(i->i).sum();
            wObj.stream().sorted().forEach(System.out::println);
            System.out.println(String.format("Знайдено %s помилок", count));
        } else {
            System.out.println("помилок не знайдено");
        }


        System.out.println("пошук зайняв " + (System.currentTimeMillis() - time) / 1000 + " сек.");



/*

        String regular = "[+] *data *\\(?[^`]\\b(?i)%s\\b";
        String obj = "User_details_response";
        String line1 = "        + data (User_details_response`)\n";
        Pattern pattern = Pattern.compile(String.format(regular,obj));
        Matcher matcher = pattern.matcher(line1);
        System.out.println(matcher.find());
*/

    }
}
