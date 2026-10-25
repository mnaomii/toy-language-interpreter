package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    // E3: evaluate left, then right; both must be int; result is int
    // errors: operand not an int, division by zero -> ExpressionException
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof IntValue(int lval)))
            throw new ExpressionException("first operand is not an integer");

        if (!(right.eval(symTable) instanceof IntValue(int rval)))
            throw new ExpressionException("second operand is not an integer");

        return switch (op) {
            case ADD -> new IntValue(lval + rval);
            case SUB -> new IntValue(lval - rval);
            case MUL -> new IntValue(lval * rval);
            case DIV -> {
                if (rval == 0) throw new ExpressionException("division by zero");
                yield new IntValue(lval / rval);
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
