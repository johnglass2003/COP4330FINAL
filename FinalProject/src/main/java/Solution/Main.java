package Solution;


import CommandHandle.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;



public class Main {

    public static void main(String[] args) {
        Validator intValidator = new IntegerValidator();
        ErrorHandler typeErrorHandler = new TypeErrorHandler();

        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument("first", Argument.Type.INTEGER, intValidator, typeErrorHandler));
        arguments.add(new Argument("second", Argument.Type.INTEGER, intValidator, typeErrorHandler));

        Consumer<List<String>> addFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 + num2));
        };

        Command addCommand = new Command("add", arguments, addFunction);

        CommandManager manager = new CommandManager();
        manager.registerCommand("add", addCommand);

        // Execute with correct and incorrect parameters
        manager.executeCommand("add", List.of("5", "7")); // Output should be "Result: 12"
        manager.executeCommand("add", List.of("5", "hello")); // Error message about the type
    }

    // Validator to check if a string is an integer
    public static class IntegerValidator implements Validator {
        @Override
        public boolean validate(String value) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    // ErrorHandler to report non-integer values
    public static class TypeErrorHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            System.err.println("Type Error: Expected an integer but received '" + error + "'");
        }
    }



}




