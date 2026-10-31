package interpreter.model.exp;

public enum RelOp {
    LT("<"), LE("<="), EQ("=="), NE("!="), GT(">"), GE(">=");
    private final String sym;
    RelOp(String sym) { this.sym = sym; }
    @Override public String toString() { return sym; }

}
