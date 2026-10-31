package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(RelOp op, IExp left, IExp right) implements IExp {
    // E5: evaluate left, then right; both must be int; result is bool
    // errors: operand not an int -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof IntValue(int lval)))
            throw new ExpressionException("first operand is not an integer");

        if (!(right.eval(symTable) instanceof IntValue(int rval)))
            throw new ExpressionException("second operand is not an integer");

        return new BoolValue(switch (op) {
            case LT -> lval < rval;
            case LE -> lval <= rval;
            case EQ -> lval == rval;
            case NE -> lval != rval;
            case GT -> lval > rval;
            case GE -> lval >= rval;
        });
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
