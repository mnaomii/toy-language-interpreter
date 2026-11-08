package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();   // the current program state

    void logPrgStateExec() throws MyException;   // appends the current state to the log file
}
