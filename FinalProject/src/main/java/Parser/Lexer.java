package Parser;

import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final CharStream chars;

    Lexer(String input) {
        chars = new CharStream(input);
    }

    //we want the format to be (commandname[argument1,argument2,...])

    List<Token> lex() throws ParseException {
        List<Token> tokenList = new ArrayList<>();

        while(chars.has(0))
        {
            if ((peek(" ") || peek("\b") || peek("\n") ||  peek("\r") || peek("\t")))
            {
                chars.advance(1);
            }
            else
            {
                tokenList.add(lexToken());
            }
        }
        return tokenList;
    }

    private Token lexNumber() {
        if(match("-"))
        {
            if(match("0"))
            {
                if(match("\\."))
                {
                    if (peek("\\d")) {
                        while (match("\\d"));
                        return chars.emit(Token.Type.DECIMAL);
                    }
                }
            }
            else {
                while(match("\\d"));
                if(match("\\.")){
                    if (peek("\\d")) {
                        while (match("\\d"));
                        return chars.emit(Token.Type.DECIMAL);
                    }
                }
                else{
                    return chars.emit(Token.Type.INTEGER);
                }
            }
        }
        else
        {
            if(match("0"))
            {
                if(match("\\."))
                {
                    while(match("\\d"));
                    return chars.emit(Token.Type.DECIMAL);
                }
            }
            else {
                while(match("\\d"));
                if(match("\\.")){
                    if (peek("\\d")) {
                        while (match("\\d"));
                        return chars.emit(Token.Type.DECIMAL);
                    }
                }
                else{
                    return chars.emit(Token.Type.INTEGER);
                }
            }

        }
        return chars.emit(Token.Type.INTEGER);
        }

    private Token lexOperator() throws ParseException {
        if(peek("!", "="))
        {
            match("!");
            match("=");
            return chars.emit(Token.Type.OPERATOR);
        }
        if(peek("=", "="))
        {
            match("=");
            match("=");
            return chars.emit(Token.Type.OPERATOR);
        }
        if(peek("&", "&"))
        {
            match("&");
            match("&");
            return chars.emit(Token.Type.OPERATOR);
        }
        if(peek("\\|", "\\|"))
        {
            match("\\|");
            match("\\|");
            return chars.emit(Token.Type.OPERATOR);
        }
        if(match("[^\\\\s]"))
        {
            return chars.emit(Token.Type.OPERATOR);
        }
        throw new ParseException("Invalid String");
    }


    private Token lexIdentifier() {
            if(match("(@|[A-Za-z])"));
            while(match("[A-Za-z0-9_-]"));
            return chars.emit(Token.Type.IDENTIFIER);
        }

    public Token lexToken() throws ParseException {
        if (peek("(@|[A-Za-z])")) { //identifier
            return lexIdentifier();
        }
        else if (peek("-|\\d")) { //number or +/-
            return lexNumber();
        }
        else { //operator
            return lexOperator();
        }
    }

    //these are from blackjack practical
    private boolean peek(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (!chars.has(i) || !test(objects[i], chars.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Object... objects) {
        boolean peek = peek(objects);
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
            Token token = new Token(type, input.substring(index, index + length));
            index += length;
            length = 0;
            return token;
        }

    }

}
