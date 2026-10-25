package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp exp) implements IStmt {
    // S4: append eval(exp) to Out
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
