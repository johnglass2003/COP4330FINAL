package oop.project.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ScenariosTests {

    @Nested
    class Add {

        @ParameterizedTest
        @MethodSource
        public void testAdd(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testAdd() {
            return Stream.of(
                    Arguments.of("Add", "(add[1,2])",
                            new ParsedCommand("add", Arrays.asList(
                                    new Token(Token.Type.NUMBER, "1"),
                                    new Token(Token.Type.NUMBER, "2")
                            ))),
                    Arguments.of("Missing Argument", "(add[1])", null),
                    Arguments.of("Extraneous Argument", "(add[1,2,3])", null),
                    Arguments.of("Not A Number", "(add[one,two])", null),
                    Arguments.of("Not An Integer", "(add[1.0,2.0])", null)
            );
        }

    }

    @Nested
    class Sub {

        @ParameterizedTest
        @MethodSource
        public void testSub(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSub() {
            return Stream.of(
                    Arguments.of("Sub", "(sub[1,2])",
                            new ParsedCommand("sub", Arrays.asList(
                                    new Token(Token.Type.NUMBER, "1"),
                                    new Token(Token.Type.NUMBER, "2")
                            ))),
                    Arguments.of("Missing Argument", "sub 1", null),
                    Arguments.of("Extraneous Argument", " 1 2 3", null),
                    Arguments.of("Not A Number", "add one two", null),
                    Arguments.of("Not An Integer", "add 1.0 2.0", null)
            );
        }

    }
    
    @Nested
    class Date {

        @ParameterizedTest
        @MethodSource
        public void testDate(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testDate() {
            return Stream.of(
                    Arguments.of("Date", "(date[2024-01-01])", new ParsedCommand("date", Arrays.asList(
                            new Token(Token.Type.IDENTIFIER, "2024-01-01")
                    ))),
                    Arguments.of("Invalid", "date 20240401", null)
            );
        }

    }

    class Div {

        @ParameterizedTest
        @MethodSource
        public void testDiv(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testDiv() {
            return Stream.of(
                    Arguments.of("Sub", "(sub[1,2])",
                            new ParsedCommand("sub", Arrays.asList(
                                    new Token(Token.Type.NUMBER, "1"),
                                    new Token(Token.Type.NUMBER, "2")
                            ))),
                    Arguments.of("Missing Argument", "div 1", null),
                    Arguments.of("Extraneous Argument", " 1 2 3", null),
                    Arguments.of("Not A Number", "div one two", null),
                    Arguments.of("Doubles less than one", "div 1.0 2.0", null),
                    Arguments.of("Integers", "div 6 2", null),
                    Arguments.of("Integers less than one", "div 1 2", null)
            );
        }

    }

    class Sqrt {

        @ParameterizedTest
        @MethodSource
        public void testSqrt(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSqrt() {
            return Stream.of(
                    Arguments.of("Sub", "(sub[1,2])",
                            new ParsedCommand("sub", Arrays.asList(
                                    new Token(Token.Type.NUMBER, "1"),
                                    new Token(Token.Type.NUMBER, "2")
                            ))),
                    Arguments.of("Missing Argument", "sqrt", null),
                    Arguments.of("Extraneous Argument", " 1 2", null),
                    Arguments.of("Not A Number", "sqrt one", null),
                    Arguments.of("Doubles less than one", "sqrt 2.0", null),
                    Arguments.of("Integers", "sqrt 4", null),
                    Arguments.of("Integers less than one", "sqrt 2", null)
            );
        }

    }

    class Mult {

        @ParameterizedTest
        @MethodSource
        public void testMult(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testMult() {
            return Stream.of(
                    Arguments.of("Sub", "(sub[1,2])",
                            new ParsedCommand("sub", Arrays.asList(
                                    new Token(Token.Type.NUMBER, "1"),
                                    new Token(Token.Type.NUMBER, "2")
                            ))),
                    Arguments.of("Missing Argument", "mult 1", null),
                    Arguments.of("Extraneous Argument", " 1 2 3", null),
                    Arguments.of("Not A Number", "mult one two", null),
                    Arguments.of("Doubles", "mult 3.0 2.0", null),
                    Arguments.of("Integers", "mult 6 2", null),
                    Arguments.of("Integers times one", "mult 1 2", null)
            );
        }

    }

    private static void test(String command, Object expected)  {
        if (expected != null) {
            try {
                var result = RetrieveCommand.parse(command);
                Assertions.assertEquals(expected, result);
            } catch (ParseException e) {
                throw new RuntimeException("parse Exception");
            }

        } else {
            //TODO: Update with your specific Exception class or whatever other
            //error handling model you use to check for specific library issues.
            Assertions.assertThrows(Exception.class, () -> {
                var scenario = new Scenarios();
                scenario.getManager().executeCommand(RetrieveCommand.parse(command));
            });
        }
    }

}
