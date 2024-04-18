package Parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final TokenStream tokens;
    Parser(List<Token> tokens) {
        this.tokens = new TokenStream(tokens);
    }

    ParsedCommand parse() throws ParseException {
        throw new RuntimeException("TODO");
    }

    //from blackjack practical
    private boolean peek(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (!tokens.has(i) || !test(objects[i], tokens.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Object... objects) {
        boolean peek = peek(objects);
        if (peek) {
            tokens.advance(objects.length);
        }
        return peek;
    }

    private static boolean test(Object object, Token token) {
        if (object instanceof Token.Type) {
            Token.Type type = (Token.Type) object;
            return token.type == type;
        } else if (object instanceof String) {
            String value = (String) object;
            return token.value.equals(value);
        } else if (object instanceof List<?>) {
            List<?> options = (List<?>) object;
            return options.stream().anyMatch(o -> test(o, token));
        } else {
            throw new AssertionError(object);
        }
    }

    private static final class TokenStream {

        private final List<Token> tokens;
        private int index = 0;

        private TokenStream(List<Token> tokens) {
            this.tokens = tokens;
        }

        public boolean has(int offset) {
            return index + offset < tokens.size();
        }

        public Token get(int offset) {
            if (!has(offset)) {
                throw new IllegalArgumentException("Broken lexer invariant.");
            }
            return tokens.get(index + offset);
        }

        public void advance(int tokens) {
            index += tokens;
        }

    }

}
