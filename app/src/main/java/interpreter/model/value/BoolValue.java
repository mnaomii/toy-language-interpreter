package interpreter.model.value;

import interpreter.model.type.IType;

public record BoolValue(boolean val) implements IValue {
    // the type of a BoolValue is bool
    @Override
    public IType getType() {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString so true prints as "true"
}
