package Parser;

record Token(
        Type type,
        String value
) {

    enum Type {
        IDENTIFIER,
        INTEGER,
        DECIMAL,
        STRING,
        OPERATOR
    }

}
