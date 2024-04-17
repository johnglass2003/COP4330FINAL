package CommandHandle;

import CommandHandle.Argument;
import CommandHandle.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
    }

    // Method to register a command with the manager
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    // Method to execute a command by its name with a list of string parameters
    public void executeCommand(String commandName, List<String> parameters) {
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("CommandHandle.Command not found: " + commandName);
            return;
        }

        List<Argument> arguments = command.getArguments();
        if (arguments.size() != parameters.size()) {
            System.out.println("Error: Incorrect number of parameters provided for " + commandName);
            return;
        }

        // Validate each argument with the provided parameters
        for (int i = 0; i < parameters.size(); i++) {
            Argument arg = arguments.get(i);
            String param = parameters.get(i);

            if (!arg.validate(param)) {
                arg.getErrorHandler().handleError(param);
                return;
            }
        }

        // If validation passes, execute the command with the updated parameters
        command.execute(parameters);
    }
}
