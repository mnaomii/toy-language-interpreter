package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    // S4: print(7) puts 7 in Out
    @Test
    void printAddsTheValueToOut() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(7))).execute(state);
        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    // print(x) with x not declared gives an ExpressionException
    @Test
    void printOfUndefinedVariableThrows() {
        IStmt stmt = new PrintStmt(new VarExp("x"));
        assertThrows(ExpressionException.class, () -> stmt.execute(state));
    }
}
