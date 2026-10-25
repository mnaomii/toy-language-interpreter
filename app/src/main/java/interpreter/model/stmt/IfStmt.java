package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    // S5: c = eval(cond); push thenS if c is true, otherwise push elseS
    // errors: c is not a bool -> StatementException
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
