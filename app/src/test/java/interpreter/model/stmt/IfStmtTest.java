package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;
    private IStmt thenS;
    private IStmt elseS;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
        thenS = new PrintStmt(new ValueExp(new IntValue(1)));
        elseS = new PrintStmt(new ValueExp(new IntValue(2)));
    }

    // S5: if true, the then branch goes on top of the stack
    @Test
    void trueConditionPushesThenBranch() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS).execute(state);
        assertEquals(thenS, state.getExeStack().pop());
    }

    // S5: if false, the else branch goes on top of the stack
    @Test
    void falseConditionPushesElseBranch() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS).execute(state);
        assertEquals(elseS, state.getExeStack().pop());
    }

    // if 1 then ... gives a StatementException
    @Test
    void nonBooleanConditionThrows() {
        IStmt stmt = new IfStmt(new ValueExp(new IntValue(1)), thenS, elseS);
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}
