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

        List myList1 = new ArrayList();
        myList1.add("5");
        myList1.add("7");
        List myList2 = new ArrayList();
        myList2.add("5");
        myList2.add("Hello");
        // Execute with correct and incorrect parameters
        manager.executeCommand("add", myList1); // Output should be "Result: 12"
        manager.executeCommand("add", myList2); // Error message about the type
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




