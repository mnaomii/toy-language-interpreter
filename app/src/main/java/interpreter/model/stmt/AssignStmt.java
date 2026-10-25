package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record AssignStmt(String id, IExp exp) implements IStmt {
    // S2: v = eval(exp), then update id -> v in the SymTable
    // errors: id not declared, type of v differs from the type of id -> StatementException
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
