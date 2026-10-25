package interpreter.exceptions;

// empty execution stack, variable declared twice or not declared, type mismatch, non-boolean condition
public class StatementException extends MyException {
    public StatementException(String message) {
        super(message);
    }
}
