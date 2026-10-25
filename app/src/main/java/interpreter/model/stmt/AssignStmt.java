package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {
    // S2: v = eval(exp), then update id -> v in the SymTable
    // errors: id not declared, type of v differs from the type of id -> StatementException
    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue v;
        try {
            v = state.getSymTable().lookup(id);
        } catch (MyException e) { throw new StatementException(e.getMessage()); }

        if( v == null) throw new StatementException("Variable not declared.");

        IValue result = exp.eval(state.getSymTable());
        if( !( result.getType().equals(v.getType()) ) ) throw new StatementException("Assignment to wrong type.");

        state.getSymTable().update(id, result);
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp;
    }
}
