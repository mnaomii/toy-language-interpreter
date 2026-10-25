package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    // S2: v = 2 stores 2 in v
    @Test
    void assignsValueToDeclaredVariable() throws MyException {
        state.getSymTable().put("v", new IntValue(0));
        new AssignStmt("v", new ValueExp(new IntValue(2))).execute(state);
        assertEquals(new IntValue(2), state.getSymTable().lookup("v"));
    }

    // v = 2 without declaring v first gives a StatementException
    @Test
    void assignmentToUndeclaredVariableThrows() {
        IStmt stmt = new AssignStmt("v", new ValueExp(new IntValue(2)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    // putting true into an int variable gives a StatementException
    @Test
    void typeMismatchThrows() {
        state.getSymTable().put("v", new IntValue(0));
        IStmt stmt = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}
