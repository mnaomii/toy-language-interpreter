package interpreter.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

// text menu built from commands: adding an option means adding a command, the menu stays the same
public class TextMenu {
    private final Map<String, Command> commands = new LinkedHashMap<>();   // keeps the order they were added

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command c : commands.values())
            System.out.printf("%4s : %s%n", c.getKey(), c.getDescription());
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            if (!scanner.hasNextLine()) return;   // input was closed, nextLine() would throw
            Command c = commands.get(scanner.nextLine().strip());
            if (c == null) {
                System.out.println("Invalid option");
                continue;
            }
            c.execute();
        }
    }
}
