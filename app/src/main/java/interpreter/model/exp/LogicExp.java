package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements IExp {
    // E4: evaluate left, then right; both must be bool; result is bool
    // errors: operand not a bool -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof BoolValue(boolean lval)))
            throw new ExpressionException("first operand is not a bool");

        if (!(right.eval(symTable) instanceof BoolValue(boolean rval)))
            throw new ExpressionException("second operand is not a bool");

        return switch (op) {
            case AND -> new BoolValue(lval && rval);
            case OR -> new BoolValue (lval || rval);
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
