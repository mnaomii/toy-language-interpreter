package interpreter.exceptions;

// invalid ADT operation: pop from an empty stack, lookup of a missing key, index out of bounds
public class ADTException extends MyException {
    public ADTException(String message) {
        super(message);
    }
}
