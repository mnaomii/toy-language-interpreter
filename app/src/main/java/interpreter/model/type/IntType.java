package interpreter.model.type;

import interpreter.model.value.IValue;

public record IntType() implements IType {
    // default(int) = 0
    @Override
    public IValue defaultValue() {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString to return "int"
}
