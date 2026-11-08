package interpreter.exceptions;

// the log file cannot be written, e.g. the folder does not exist
public class RepositoryException extends MyException {
    public RepositoryException(String message) {
        super(message);
    }
}
