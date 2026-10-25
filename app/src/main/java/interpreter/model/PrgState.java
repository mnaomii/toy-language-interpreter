package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    // stores the three structures and the program, then pushes the program on the ExeStack
    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IStmt program) {
        throw new UnsupportedOperationException("TODO");
    }

    public IStack<IStmt> getExeStack() {
        throw new UnsupportedOperationException("TODO");
    }

    public IDictionary<String, IValue> getSymTable() {
        throw new UnsupportedOperationException("TODO");
    }

    public IList<IValue> getOut() {
        throw new UnsupportedOperationException("TODO");
    }

    public IStmt getOriginalProgram() {
        throw new UnsupportedOperationException("TODO");
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        throw new UnsupportedOperationException("TODO");
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        throw new UnsupportedOperationException("TODO");
    }

    public void setOut(IList<IValue> out) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString to show ExeStack, SymTable and Out
}
