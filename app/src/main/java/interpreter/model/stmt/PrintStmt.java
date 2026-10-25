package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;


public record PrintStmt(IExp exp) implements IStmt {
    // S4: append eval(exp) to Out
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue result;

        result = exp.eval(state.getSymTable());

        state.getOut().add(result);

        return state;

    }

    @Override
    public String toString() {
        return "print("  + exp + ")";
    }
}
