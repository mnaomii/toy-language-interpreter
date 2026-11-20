package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;

// an expression computes a value; it reads the SymTable but never changes it
public interface IExp {
    IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException;
}
