package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;

public record IntValue(int val) implements IValue {
    // the type of an IntValue is int
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
