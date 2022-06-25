package com.gmail.wformat.util;

import com.gmail.wformat.entitys.Case;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegularExpressionsTest {
    static List<Case> cases;

    @BeforeAll
    static void buildCases() {
        var context = new ClassPathXmlApplicationContext("appContext.xml");
        cases = new ArrayList<>();
        Case incl = context.getBean("include", Case.class);
        Case attr = context.getBean("attribute", Case.class);
        Case d_arr = context.getBean("data_array", Case.class);
        Case data = context.getBean("data", Case.class);
        Case full = context.getBean("fullOptions", Case.class);
        cases.add(0, incl);
        cases.add(1, attr);
        cases.add(2, d_arr);
        cases.add(3, data);
        cases.add(4, full);
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

    @Nested
    static class BuildCasesParamInclude implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            String regularInclude = cases.get(0).getRegExp();
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
            String regularAttributes = cases.get(1).getRegExp();
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
            String regularDataArray = cases.get(2).getRegExp();
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
            String regularData = cases.get(3).getRegExp();
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
            String regularFull = cases.get(4).getRegExp();
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