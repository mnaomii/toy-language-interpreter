package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public record CompStmt(IStmt first, IStmt second) implements IStmt {
    // S1: push second, then first (so first runs next)
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
