package interpreter.exceptions;

// base class of all interpreter errors (checked exception)
public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}
