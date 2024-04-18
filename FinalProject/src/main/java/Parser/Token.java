package Parser;
public class Token {

    public enum Type {
        IDENTIFIER,
        INTEGER,
        DECIMAL,
        STRING,
        OPERATOR
    }

    public Type type;
    public String value;

    public Token(Type type, String literal) {
        this.type = type;
        this.value = literal;
    }


}