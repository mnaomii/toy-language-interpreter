package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;

public record VarDeclStmt(String name, IType type) implements IStmt {
    // S3: add name -> default value of type to the SymTable
    // errors: name already declared -> StatementException
    @Override
    public PrgState execute(PrgState state) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
