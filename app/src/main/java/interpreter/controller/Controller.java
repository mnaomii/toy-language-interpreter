package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        throw new UnsupportedOperationException("TODO");
    }

    // pops the top statement and executes it (one rule S1-S6)
    // errors: empty ExeStack -> StatementException
    public PrgState oneStep(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // takes the current program from the repository and runs oneStep until the ExeStack is empty,
    // displaying the state at the start and after every step
    public void allStep() throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // prints the state, but only if displayFlag is on
    private void display(PrgState state) {
        throw new UnsupportedOperationException("TODO");
    }
}
