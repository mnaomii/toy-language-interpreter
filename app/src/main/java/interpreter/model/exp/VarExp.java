package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    // E2: looks id up in the SymTable; not defined -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {

        if(symTable.isDefined(id)){
            return symTable.lookup(id);
        }
        throw new ExpressionException("The variable " + id + " is not defined");
    }

    @Override
    public String toString(){
        return id;
    }

}
