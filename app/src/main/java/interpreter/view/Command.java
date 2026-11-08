package interpreter.view;

// one option of the text menu: the key the user types, a description, and what it does
public abstract class Command {
    private final String key;
    private final String description;

    protected Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
