package com.gmail.wformat.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildCasesTest {
    @BeforeAll
    static void buildCases() {
        BuildCases.addCaseInclude();//0
        BuildCases.addCaseAttribute();//1
        BuildCases.addCaseDataArr();//2
        BuildCases.addCaseData();//3
        BuildCases.addFullOptions();//4
        BuildCases.addManualOptions("test-b%1$s-test");//5
    }


    @ParameterizedTest
    @ArgumentsSource(BuildCasesParamInclude.class)
    void regularInclude(Matcher matcher, Boolean expected) {
        assertEquals(expected, matcher.find());
    }

    @ParameterizedTest
    @ArgumentsSource(BuildCasesParamAttributes.class)
    void regularAttributes(Matcher matcher, Boolean expected) {
        assertEquals(expected, matcher.find());
    }

    @ParameterizedTest
    @ArgumentsSource(BuildCasesParamDataArray.class)
    void regularDataArray(Matcher matcher, Boolean expected) {
        assertEquals(expected, matcher.find());
    }

    @ParameterizedTest
    @ArgumentsSource(BuildCasesParamData.class)
    void regularData(Matcher matcher, Boolean expected) {
        assertEquals(expected, matcher.find());
    }

    @ParameterizedTest
    @ArgumentsSource(BuildCasesParamFull.class)
    void regularFull(Matcher matcher, Boolean expected) {
        assertEquals(expected, matcher.find());
    }

    @Test
    void regularManual() {
        String regularManual = BuildCases.getCases().get(5).getRegExp();
        String nameCaseManual = BuildCases.getCases().get(5).getName();
        assertEquals("test-b%1$s-test", regularManual);
        assertEquals("manualOptions", nameCaseManual);
    }

    @Nested
    static class BuildCasesParamInclude implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
//            BuildCases.addCaseInclude();
            String regularInclude = BuildCases.getCases().get(0).getRegExp();
            return Stream.of(
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include (Test_0, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include (`Test_0, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include (Test_0`, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include (`Test_0`, required)"), false),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include Test_0"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include Test_0`"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include `Test_0"), true),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include `Test_0`"), false),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + include Test_0_Other "), false),
                    Arguments.of(Pattern.compile(String.format(regularInclude, "Test_0")).matcher(" + notinclude Test_0 "), false)
            );
        }
    }

    @Nested
    static class BuildCasesParamAttributes implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            String regularAttributes = BuildCases.getCases().get(1).getRegExp();
            return Stream.of(
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher(" + Attributes (Test_1, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher("  + Attributes (`Test_1, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher("  + Attributes (Test_1`, required)"), true),
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher("  + Attributes (`Test_1`, required)"), false),
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher(" + Attributes (Test_1_Other) "), false),
                    Arguments.of(Pattern.compile(String.format(regularAttributes, "Test_1")).matcher(" + NotAttributes (Test_1) "), false)
            );
        }
    }

    @Nested
    static class BuildCasesParamDataArray implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            String regularDataArray = BuildCases.getCases().get(2).getRegExp();
            return Stream.of(
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher(" + data (array[Test_2])"), true),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + data (array[`Test_2])"), true),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + data (array[ `Test_2])"), true),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + data (array[Test_2` ])"), true),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + data (array[ Test_2` ])"), true),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + data (array[Not_Test_2])"), false),
                    Arguments.of(Pattern.compile(String.format(regularDataArray, "Test_2")).matcher("    + notdata (array[Test_2])"), false)
            );
        }
    }

    @Nested
    static class BuildCasesParamData implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            String regularData = BuildCases.getCases().get(3).getRegExp();
            return Stream.of(
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data (Test_3)"), true),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data (`Test_3)"), true),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data (Test_3`)"), true),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data (`Test_3`)"), false),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data ( Test_3 ) )"), true),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + data (Not_Test_3)"), false),
                    Arguments.of(Pattern.compile(String.format(regularData, "Test_3")).matcher("     + not_data (`Test_3`)"), false)
            );
        }
    }

    @Nested
    static class BuildCasesParamFull implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            String regularFull = BuildCases.getCases().get(4).getRegExp();
            return Stream.of(
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data (Test_4)"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data (`Test_4)"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data (Test_4`)"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data (`Test_4`)"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data ( Test_4 ) )"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + data (Not_Test_4)"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + not_data (`Test_4`)"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + include (Test_4)"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("     + Attributes (`Test_4)"), true),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher(" #  Test_4`"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher(" #  `Test_4"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("  something text - Test_4`"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("  something text - `Test_4"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("  without plus on start line Test_4`"), false),
                    Arguments.of(Pattern.compile(String.format(regularFull, "Test_4")).matcher("  without plus on start line `Test_4"), false)
            );
        }
    }
}