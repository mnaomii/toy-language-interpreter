package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.ViewException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

// text menu with two options: select an example program, run the selected program
// errors are caught here and shown as messages; the menu must not crash
public class View {
    private IStmt program = null;   // the selected program, null until one is picked

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println();
            System.out.println("1. Select program");
            System.out.println("2. Run program");
            System.out.println("0. Exit");
            System.out.print("Option: ");

            if(!scanner.hasNextLine()) return;   // input was closed
            String option = scanner.nextLine();

            try {
                if(option.equals("1")) {
                    selectProgram(scanner);
                } else if(option.equals("2")) {
                    runProgram();
                } else if(option.equals("0")) {
                    return;
                } else {
                    throw new ViewException("Unknown option.");
                }
            } catch(MyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void selectProgram(Scanner scanner) throws ViewException {
        System.out.println("1. " + Examples.EX1);
        System.out.println("2. " + Examples.EX2);
        System.out.println("3. " + Examples.EX3);
        System.out.print("Program: ");

        if(!scanner.hasNextLine()) throw new ViewException("No program selected.");
        String choice = scanner.nextLine();

        if(choice.equals("1")) program = Examples.EX1;
        else if(choice.equals("2")) program = Examples.EX2;
        else if(choice.equals("3")) program = Examples.EX3;
        else throw new ViewException("No program with number " + choice + ".");
    }

    // builds a fresh state every time, so the same program can be run again
    private void runProgram() throws MyException {
        if(program == null) throw new ViewException("Select a program first.");

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        Controller controller = new Controller(new Repository(state), true);
        controller.allStep();
    }
}
