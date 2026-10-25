package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public record NopStmt() implements IStmt {
    // S6: does nothing
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
