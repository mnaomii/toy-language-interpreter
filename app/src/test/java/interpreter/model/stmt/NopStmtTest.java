package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    // S6: nop leaves the variables and Out as they were
    @Test
    void changesNothing() throws MyException {
        state.getSymTable().put("v", new IntValue(1));
        new NopStmt().execute(state);
        assertEquals(new IntValue(1), state.getSymTable().lookup("v"));
        assertEquals(0, state.getOut().size());
    }
}
