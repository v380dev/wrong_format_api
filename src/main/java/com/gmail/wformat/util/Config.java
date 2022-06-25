package com.gmail.wformat.util;

import java.io.*;
import java.util.Properties;

public class Config {

    public final String THREADS;
    private final String FILE_PROP;

    public final String INPUT_FILE_NAME;

    private final String CASES_COMMENT;
    public final String CASE_INCLUDE;
    public final String CASE_ATTRIBUTE;
    public final String CASE_DATA_ARRAY;
    public final String CASE_DATA;
    public final String CASE_FULL_OPTIONS;

    public final String OUT_FILE_PREFIX;
    public final String OUT_FILE_FORM_DATE;

    public final String INPUT_FILE_NAME_VAL;

    public final int CASE_INCLUDE_VAL;
    public final int CASE_ATTRIBUTE_VAL;
    public final int CASE_DATA_ARRAY_VAL;
    public final int CASE_DATA_VAL;
    public final int CASE_FULL_OPTIONS_VAL;

    public final String OUT_FILE_PREFIX_VAL;
    public final String OUT_FILE_FORM_DATE_VAL;

    public final int THREADS_VAL;
    private final String THREADS_COMMENT;

    public Config(
            String FILE_PROP,
            String INPUT_FILE_NAME,
            String INPUT_FILE_NAME_VAL,
            String CASES_COMMENT,
            String CASE_INCLUDE,
            int CASE_INCLUDE_VAL,
            String CASE_ATTRIBUTE,
            int CASE_ATTRIBUTE_VAL,
            String CASE_DATA_ARRAY,
            int CASE_DATA_ARRAY_VAL,
            String CASE_DATA,
            int CASE_DATA_VAL,
            String CASE_FULL_OPTIONS,
            int CASE_FULL_OPTIONS_VAL,
            String OUT_FILE_PREFIX,
            String OUT_FILE_PREFIX_VAL,
            String OUT_FILE_FORM_DATE,
            String OUT_FILE_FORM_DATE_VAL,
            String THREADS_COMMENT,
            String THREADS,
            int THREADS_VAL
    ) {
        this.THREADS = THREADS;
        this.FILE_PROP = FILE_PROP;
        this.INPUT_FILE_NAME = INPUT_FILE_NAME;
        this.CASES_COMMENT = CASES_COMMENT;
        this.CASE_INCLUDE = CASE_INCLUDE;
        this.CASE_ATTRIBUTE = CASE_ATTRIBUTE;
        this.CASE_DATA_ARRAY = CASE_DATA_ARRAY;
        this.CASE_DATA = CASE_DATA;
        this.CASE_FULL_OPTIONS = CASE_FULL_OPTIONS;
        this.OUT_FILE_PREFIX = OUT_FILE_PREFIX;
        this.OUT_FILE_FORM_DATE = OUT_FILE_FORM_DATE;
        this.INPUT_FILE_NAME_VAL = INPUT_FILE_NAME_VAL;
        this.CASE_INCLUDE_VAL = CASE_INCLUDE_VAL;
        this.CASE_ATTRIBUTE_VAL = CASE_ATTRIBUTE_VAL;
        this.CASE_DATA_ARRAY_VAL = CASE_DATA_ARRAY_VAL;
        this.CASE_DATA_VAL = CASE_DATA_VAL;
        this.CASE_FULL_OPTIONS_VAL = CASE_FULL_OPTIONS_VAL;
        this.OUT_FILE_PREFIX_VAL = OUT_FILE_PREFIX_VAL;
        this.OUT_FILE_FORM_DATE_VAL = OUT_FILE_FORM_DATE_VAL;
        this.THREADS_VAL = THREADS_VAL;
        this.THREADS_COMMENT = THREADS_COMMENT;
    }

    public Properties getPropertiesFile() {
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

    private void buildNewPropertiesFile() {
        try (Writer w = new FileWriter(FILE_PROP);) {
            w.write(String.format("%s=%s\n\n", INPUT_FILE_NAME, INPUT_FILE_NAME_VAL));
            w.write(String.format("%s\n", CASES_COMMENT));
            w.write(String.format("%s=%s\n", CASE_INCLUDE, CASE_INCLUDE_VAL));
            w.write(String.format("%s=%s\n", CASE_ATTRIBUTE, CASE_ATTRIBUTE_VAL));
            w.write(String.format("%s=%s\n", CASE_DATA_ARRAY, CASE_DATA_ARRAY_VAL));
            w.write(String.format("%s=%s\n", CASE_DATA, CASE_DATA_VAL));
            w.write(String.format("%s=%s\n\n", CASE_FULL_OPTIONS, CASE_FULL_OPTIONS_VAL));
            w.write(String.format("%s=%s\n", OUT_FILE_PREFIX, OUT_FILE_PREFIX_VAL));
            w.write(String.format("%s=%s\n\n", OUT_FILE_FORM_DATE, OUT_FILE_FORM_DATE_VAL));
            w.write(String.format("%s\n", THREADS_COMMENT));
            w.write(String.format("%s=%s", THREADS, THREADS_VAL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}