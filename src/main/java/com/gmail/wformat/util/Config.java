package com.gmail.wformat.util;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final String FILE_PROP = "app_wr.config";

    public static final String INPUT_FILE_NAME = "app.input_file";
    public static final String SPLITTER = "app.splitter";

    private static final String SET_CASES = "#cases: 1 включено, 0 вимкнено";
    public static final String CASE_INCLUDE = "app.case.include";
    public static final String CASE_ATTRIBUTE = "app.case.attribute";
    public static final String CASE_DATA_ARRAY = "app.case.data_array";
    public static final String CASE_DATA = "app.case.data";

    public static final String OUT_FILE_PREFIX = "app.out_file_name_prefix";
    public static final String OUT_FILE_FORM_DATE = "app.out_file_form_date";

    public static final String MANUAL_WRITE = "app.manual_write";

    public static final String INPUT_FILE_NAME_VAL = "apiary.apib";
    public static final String SPLITTER_VAL = "@@@";

    public static final int CASE_INCLUDE_VAL = 1;
    public static final int CASE_ATTRIBUTE_VAL = 1;
    public static final int CASE_DATA_ARRAY_VAL = 1;
    public static final int CASE_DATA_VAL = 1;

    public static final String OUT_FILE_PREFIX_VAL = "wrong";
    public static final String OUT_FILE_FORM_DATE_VAL = "yyyy-MM-dd_HH-mm-ss";

    public static final String MANUAL_WRITE_VAL = "1";
    private static final String MANUAL_WRITE_COMMENT = "#app.manual_write 0 - не запитує ручного вводу, 1 - буде запит на ввод з консолі";

    public static Properties getPropertiesFile() {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(FILE_PROP)) {
            prop.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            buildNewPropertiesFile();
            try (FileInputStream in = new FileInputStream(FILE_PROP)) {
                prop.load(in);
            } catch (FileNotFoundException ex) {
                e.printStackTrace();
            } catch (IOException ex) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    private static void buildNewPropertiesFile() {
        try (Writer w = new FileWriter(FILE_PROP);) {
            w.write(String.format("%s=%s\n", INPUT_FILE_NAME, INPUT_FILE_NAME_VAL));
            w.write(String.format("%s=%s\n\n", SPLITTER, SPLITTER_VAL));

            w.write(String.format("%s\n", SET_CASES));
            w.write(String.format("%s=%s\n", CASE_INCLUDE, CASE_INCLUDE_VAL));
            w.write(String.format("%s=%s\n", CASE_ATTRIBUTE, CASE_ATTRIBUTE_VAL));
            w.write(String.format("%s=%s\n", CASE_DATA_ARRAY, CASE_DATA_ARRAY_VAL));
            w.write(String.format("%s=%s\n\n", CASE_DATA, CASE_DATA_VAL));

            w.write(String.format("%s=%s\n", OUT_FILE_PREFIX, OUT_FILE_PREFIX_VAL));
            w.write(String.format("%s=%s\n\n", OUT_FILE_FORM_DATE, OUT_FILE_FORM_DATE_VAL));
            w.write(String.format("%s\n", MANUAL_WRITE_COMMENT));
            w.write(String.format("%s=%s", MANUAL_WRITE, MANUAL_WRITE_VAL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
    //example commands
    public static void main(String[] args) {
        Properties prop = getPropertiesFile();
        String fileName = prop.getProperty(INPUT_FILE_NAME);
    }
*/


