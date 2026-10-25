package interpreter.model.type;

import interpreter.model.value.IValue;

public interface IType {
    IValue defaultValue();   // used by rule S3
}
