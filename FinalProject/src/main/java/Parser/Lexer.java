package Parser;

import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final CharStream chars;

    Lexer(String input) {
        chars = new CharStream(input);
    }

    //we want the format to be (commandname[argument1,argument2,...])

    List<Token> lex() { throw new RuntimeException("TODO"); }

    private Token lexNumber() { throw new RuntimeException("TODO"); }

    private Token lexOperator() { throw new RuntimeException("TODO"); }


    private Token lexIdentifier() { throw new RuntimeException("TODO"); }

    //these are from blackjack practical
    private boolean peek(Object... objects) {
        for (var i = 0; i < objects.length; i++) {
            if (!chars.has(i) || !test(objects[i], chars.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Object... objects) {
        var peek = peek(objects);
        if (peek) {
            chars.advance(objects.length);
        }
        return peek;
    }

    private static boolean test(Object object, char character) {
        return switch (object) {
            case Character c -> character == c;
            case String regex -> Character.toString(character).matches(regex);
            case List<?> options -> options.stream().anyMatch(o -> test(o, character));
            default -> throw new AssertionError(object);
        };
    }

    private static final class CharStream {

        private final String input;
        private int index = 0;
        private int length = 0;

        private CharStream(String input) {
            this.input = input;
        }

        public boolean has(int offset) {
            return index + length + offset < input.length();
        }

        public char get(int offset) {
            if (!has(offset)) {
                throw new IllegalArgumentException("Broken lexer invariant.");
            }
            return input.charAt(index + length + offset);
        }

        public void advance(int chars) {
            length += chars;
        }

        public Token emit(Token.Type type) {
            var token = new Token(type, input.substring(index, index + length));
            index += length;
            length = 0;
            return token;
        }

    }

}
