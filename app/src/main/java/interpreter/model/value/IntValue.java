package interpreter.model.value;

import interpreter.model.type.IType;

public record IntValue(int val) implements IValue {
    // the type of an IntValue is int
    @Override
    public IType getType() {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: override toString so 2 prints as "2"
}
