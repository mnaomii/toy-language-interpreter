package interpreter.model.exp;

public enum LogicOp {
    AND("and"), OR("or");

    // enum -> only valid choices go through

    private final String sym;
    LogicOp(String sym){ this.sym = sym;}

    @Override
    public String toString(){
        return sym;
    }
}
