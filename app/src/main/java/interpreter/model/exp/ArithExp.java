package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    // E3: evaluate left, then right; both must be int; result is int
    // errors: operand not an int, division by zero -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
