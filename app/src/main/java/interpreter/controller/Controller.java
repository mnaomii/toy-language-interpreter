package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {

        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    // pops the top statement and executes it (one rule S1-S6)
    // errors: empty ExeStack -> StatementException
    public PrgState oneStep(PrgState state) throws MyException {
        if(state.getExeStack().isEmpty()) throw new StatementException("Nothing to execute");

        IStmt statement = state.getExeStack().pop();
        statement.execute(state);

        return state;
    }

    // takes the current program from the repository and runs oneStep until the ExeStack is empty,
    // displaying the state at the start and after every step
    public void allStep() throws MyException {

        PrgState program = repo.getCrtPrg();
        if(displayFlag) display(program);

        while (!program.getExeStack().isEmpty()) {
            oneStep(program);
            if(displayFlag) display(program);

        }

    }

    // prints the state, but only if displayFlag is on
    private void display(PrgState state) {
        System.out.print(state.toString() + "\n\n");
    }
}
