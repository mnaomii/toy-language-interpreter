package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record IntType() implements IType {
    // default(int) = 0
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString(){
        return "int";
    }
}
