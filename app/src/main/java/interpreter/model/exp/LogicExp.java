package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements IExp {
    // E4: evaluate left, then right; both must be bool; result is bool
    // errors: operand not a bool -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString
}
