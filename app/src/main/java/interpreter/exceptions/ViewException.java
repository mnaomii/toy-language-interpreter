package interpreter.exceptions;

// invalid user input in the text menu, e.g. unknown option or wrong program number
public class ViewException extends MyException {
    public ViewException(String message) {
        super(message);
    }
}
