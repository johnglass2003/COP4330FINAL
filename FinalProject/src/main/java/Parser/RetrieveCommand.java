package Parser;

public class RetrieveCommand {

    //seal the whole parsing thing and make it accessible with only this class
    public static ParsedCommand parse(String input) throws ParseException {
        return new Parser(new Lexer(input).lex()).parse();
    }

}
