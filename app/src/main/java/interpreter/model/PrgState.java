package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IStack<IStmt> exeStack; // like a call stack
    private IDictionary<String, IValue> symTable; // symbols table
    private IList<IValue> out; // stdout -like
    private final IStmt originalProgram;

    // stores the four structures and the program, then pushes the program on the ExeStack
    public PrgState(IStack<IStmt> exeStack, IDictionary<String,IValue> symTable,
                    IList<IValue> out,
                    IDictionary<StringValue, BufferedReader> fileTable,
                    IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;

        this.exeStack.push(program);
    }

    public static PrgState of(IStmt program){
        return new PrgState(new MyStack<>(), new MyDictionary<>(),
                            new MyList<>(),  new MyDictionary<>(),
                            program);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    @Override
    public String toString() {
        return "Exestack : " + exeStack.toString() + "\nOut: "
                + out.toString() + "\nSymTable: " + symTable.toString();


    }
}
