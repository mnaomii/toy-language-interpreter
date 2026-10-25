package interpreter.exceptions;

// invalid execution request, e.g. a step when the execution stack is empty
public class ControllerException extends MyException {
    public ControllerException(String message) {
        super(message);
    }
}
