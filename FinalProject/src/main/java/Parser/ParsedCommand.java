package Parser;

import java.util.List;
import java.util.ArrayList;

public class ParsedCommand {
    // Field for the command name
    private String commandName;
    // List of tokens associated with the command
    private List<Token> tokens;

    // Constructor
    public ParsedCommand(String commandName) {
        this.commandName = commandName;
        this.tokens = new ArrayList<>();
    }

    // Getter for the command name
    public String getCommandName() {
        return commandName;
    }

    // Setter for the command name
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    // Getter for the tokens list
    private List<Token> getTokens() {
        return tokens;
    }

    // Setter for the tokens list
    private void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Method to add a token to the list of tokens
    private void addToken(Token token) {
        this.tokens.add(token);
    }
}
