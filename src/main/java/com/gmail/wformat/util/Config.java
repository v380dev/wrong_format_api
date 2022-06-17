package com.gmail.wformat.util;

import java.io.*;
import java.util.Properties;

public class Config {
    public static final String THREADS = "app.threads";
    private static final String FILE_PROP = "app_wr.config";

    public static final String INPUT_FILE_NAME = "app.input_file";
    public static final String SPLITTER = "app.splitter";

    private static final String CASES_COMMENT = "#cases: 1 включено, 0 вимкнено";
    public static final String CASE_INCLUDE = "app.case.include";
    public static final String CASE_ATTRIBUTE = "app.case.attribute";
    public static final String CASE_DATA_ARRAY = "app.case.data_array";
    public static final String CASE_DATA = "app.case.data";
    public static final String CASE_FULL_OPTIONS = "app.case.full_options";
    public static final String CASE_MANUAL_OPTIONS = "app.user.manual_options";
    public static final String REGULAR_MANUAL_OPTIONS = "app.user.regular_expression";

    public static final String OUT_FILE_PREFIX = "app.out_file_name_prefix";
    public static final String OUT_FILE_FORM_DATE = "app.out_file_form_date";

    private static final String MANUAL_COMMENT = "# %1%s підставляє ім'я об'єкту";

    public static final String INPUT_FILE_NAME_VAL = "apiary.apib";
    public static final String SPLITTER_VAL = "=";

    public static final int CASE_INCLUDE_VAL = 0;
    public static final int CASE_ATTRIBUTE_VAL = 0;
    public static final int CASE_DATA_ARRAY_VAL = 0;
    public static final int CASE_DATA_VAL = 0;
    public static final int CASE_FULL_OPTIONS_VAL = 0;
    public static final int CASE_MANUAL_OPTIONS_VAL = 0;
    public static final String REGULAR_MANUAL_VAL = "([\\(]\\b%1$s\\b)|(\\b%1$s\\b[\\)])";

    public static final String OUT_FILE_PREFIX_VAL = "wrong";
    public static final String OUT_FILE_FORM_DATE_VAL = "yyyy-MM-dd_HH-mm-ss";

    public static final int THREADS_VAL = 4;
    private static final String THREADS_COMMENT = "#кількість потоків:";
    private static final String MANUAL_EXAMPLE = "#example: ([\\\\(]\\\\b%1$s\\\\b)|(\\\\b%1$s\\\\b[\\\\)])";

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

            w.write(String.format("%s\n", CASES_COMMENT));
            w.write(String.format("%s=%s\n", CASE_INCLUDE, CASE_INCLUDE_VAL));
            w.write(String.format("%s=%s\n", CASE_ATTRIBUTE, CASE_ATTRIBUTE_VAL));
            w.write(String.format("%s=%s\n", CASE_DATA_ARRAY, CASE_DATA_ARRAY_VAL));
            w.write(String.format("%s=%s\n", CASE_DATA, CASE_DATA_VAL));
            w.write(String.format("%s=%s\n\n", CASE_FULL_OPTIONS, CASE_FULL_OPTIONS_VAL));
            w.write(String.format("%s\n", MANUAL_COMMENT));
            w.write(String.format("%s\n", MANUAL_EXAMPLE));
            w.write(String.format("%s=%s\n", CASE_MANUAL_OPTIONS, CASE_MANUAL_OPTIONS_VAL));
            w.write(String.format("%s=\n\n", REGULAR_MANUAL_OPTIONS));

            w.write(String.format("%s=%s\n", OUT_FILE_PREFIX, OUT_FILE_PREFIX_VAL));
            w.write(String.format("%s=%s\n\n", OUT_FILE_FORM_DATE, OUT_FILE_FORM_DATE_VAL));

            w.write(String.format("%s\n", THREADS_COMMENT));
            w.write(String.format("%s=%s", THREADS, THREADS_VAL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}