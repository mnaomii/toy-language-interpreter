package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// a reference to a heap location holding a value of type inner; inner can be another RefType
public record RefType(IType inner) implements IType {
    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }
}
