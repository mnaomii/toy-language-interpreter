package interpreter.model.type;

import interpreter.model.value.IValue;

public record BoolType() implements IType {
    // default(bool) = false
    @Override
    public IValue defaultValue() {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString to return "bool"
}
