package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    // E2: looks id up in the SymTable; not defined -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
