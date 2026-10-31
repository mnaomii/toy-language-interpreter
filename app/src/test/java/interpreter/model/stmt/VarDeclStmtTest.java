package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    // S3: int v starts as 0
    @Test
    void intVariableStartsAtZero() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    // S3: bool a starts as false
    @Test
    void boolVariableStartsAtFalse() throws MyException {
        new VarDeclStmt("a", new BoolType()).execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("a"));
    }

    // S3: string s starts as ""
    @Test
    void stringVariableStartsEmpty() throws MyException {
        new VarDeclStmt("s", new StringType()).execute(state);
        assertEquals(new StringValue(""), state.getSymTable().lookup("s"));
    }

    // declaring v twice gives a StatementException
    @Test
    void redeclaredVariableThrows() throws MyException {
        IStmt stmt = new VarDeclStmt("v", new IntType());
        stmt.execute(state);
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}
