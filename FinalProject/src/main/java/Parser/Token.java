package Parser;

record Token(
        Type type,
        String value
) {

    enum Type {
        NUMBER,
        IDENTIFIER,
        OPERATOR,
    }

}
