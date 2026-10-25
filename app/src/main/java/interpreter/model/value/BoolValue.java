package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IType;

public record BoolValue(boolean val) implements IValue {
    // the type of a BoolValue is bool
    @Override
    public IType getType() {

        return new BoolType();
    }

    @Override
    public String toString() {
        if( val) return "true";
        return "false";
    }
}
