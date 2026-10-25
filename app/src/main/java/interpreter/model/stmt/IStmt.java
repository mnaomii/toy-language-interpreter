package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

// a statement applies its rule (S1-S6) to the program state
public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
}
