package interpreter.exceptions;

// invalid repository access, e.g. asking for a program state when none is stored
public class RepositoryException extends MyException {
    public RepositoryException(String message) {
        super(message);
    }
}
